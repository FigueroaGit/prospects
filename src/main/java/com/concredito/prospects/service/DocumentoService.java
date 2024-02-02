package com.concredito.prospects.service;

import com.concredito.prospects.model.Documento;
import com.concredito.prospects.repository.DocumentoRepository;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.*;
import java.util.List;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentoService {

    private final DocumentoRepository documentoRepository;
    private final MongoTemplate mongoTemplate;
    private final GridFsTemplate gridFsTemplate;

    @Autowired
    public DocumentoService(DocumentoRepository documentoRepository, MongoTemplate mongoTemplate, GridFsTemplate gridFsTemplate) {
        this.documentoRepository = documentoRepository;
        this.mongoTemplate = mongoTemplate;
        this.gridFsTemplate = gridFsTemplate;
    }

    public Documento guardarDocumento(String prospectoId, String nombre, MultipartFile archivo, String tipoArchivo, String metadata) throws IOException {
        Documento documento = new Documento();
        documento.setProspectoId(prospectoId);
        documento.setNombre(nombre);

        // Almacenar el archivo en GridFS con metadata y tipo de archivo
        String archivoId = almacenarArchivoEnGridFS(archivo, nombre, tipoArchivo, metadata);

        // Guardar la información del documento en la colección documentos
        documento.setId(archivoId);
        return documentoRepository.save(documento);
    }

    public InputStream descargarDocumento(String documentoId) throws IOException {
        // Obtener el archivo desde GridFS
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(documentoId)));
        if (gridFSFile == null) {
            throw new IOException("Documento no encontrado");
        }

        // Devolver un InputStream para el archivo
        GridFsResource resource = gridFsTemplate.getResource(gridFSFile);
        return resource.getInputStream();
    }

    public List<Documento> obtenerDocumentosPorProspectoId(String prospectoId) {
        return documentoRepository.findByProspectoId(prospectoId);
    }

    private String almacenarArchivoEnGridFS(MultipartFile archivo, String nombre, String tipoArchivo, String metadata) throws IOException {
        // Almacenar el archivo en GridFS con metadata y tipo de archivo
        return gridFsTemplate.store(archivo.getInputStream(), nombre, tipoArchivo, metadata).toString();
    }
}