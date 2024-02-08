package com.concredito.prospects.service;

import com.concredito.prospects.model.Documento;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentoService {
    private final GridFsTemplate gridFsTemplate;
    private GridFsOperations gridFsoperations;

    public DocumentoService(GridFsTemplate gridFsTemplate, GridFsOperations gridFsoperations) {
        this.gridFsTemplate = gridFsTemplate;
        this.gridFsoperations = gridFsoperations;
    }

    public String agregarArchivo(MultipartFile subir, String nombre, String prospectoId) throws IOException {
        DBObject metadata = new BasicDBObject();
        metadata.put("tamanoArchivo", subir.getSize());
        metadata.put("prospectoId", prospectoId);

        String extension = FilenameUtils.getExtension(subir.getOriginalFilename());

        String nombreConExtension = nombre + "." + extension;

        Object fileID = gridFsTemplate.store(subir.getInputStream(), nombreConExtension, subir.getContentType(), metadata);

        return fileID.toString();
    }

    public Documento descargarArchivo(String id) throws IOException {
        GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));

        Documento documento = new Documento();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            documento.setNombre(gridFSFile.getFilename());

            documento.setTipoArchivo(gridFSFile.getMetadata().get("_contentType").toString());

            documento.setTamanoArchivo(gridFSFile.getMetadata().get("tamanoArchivo").toString());

            documento.setArchivo(IOUtils.toByteArray(gridFsoperations.getResource(gridFSFile).getInputStream()));
        }
        return documento;
    }

    public List<Documento> obtenerDocumentosPorProspectoId(String prospectoId) throws IOException {
        List<Documento> documentos = new ArrayList<>();
        Query query = new Query(Criteria.where("metadata.prospectoId").is(prospectoId));
        GridFSFindIterable gridFSFiles = gridFsTemplate.find(query);

        for (GridFSFile gridFSFile : gridFSFiles) {
            Documento documento = new Documento();
            documento.setProspectoId(gridFSFile.getMetadata().get("prospectoId").toString());
            documento.setNombre(gridFSFile.getFilename());
            documento.setTipoArchivo(gridFSFile.getMetadata().get("_contentType").toString());
            documento.setTamanoArchivo(gridFSFile.getMetadata().get("tamanoArchivo").toString());
            documento.setArchivo(IOUtils.toByteArray(gridFsoperations.getResource(gridFSFile).getInputStream()));

            documentos.add(documento);
        }

        return documentos;
    }

    public void eliminarDocumento(String id) {
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
    }
}