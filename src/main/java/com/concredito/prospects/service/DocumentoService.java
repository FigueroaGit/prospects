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

    public String agregarArchivo(MultipartFile subir, String prospectoId) throws IOException {
        DBObject metadata = new BasicDBObject();
        String prospectoIdSinComillas = prospectoId.replace("\"", "");
        metadata.put("tamanoArchivo", subir.getSize());
        metadata.put("prospectoId", prospectoIdSinComillas);

        Object fileID = gridFsTemplate.store(subir.getInputStream(), subir.getOriginalFilename(), subir.getContentType(), metadata);

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
            documento.setId(gridFSFile.getObjectId().toHexString());
            documento.setProspectoId(gridFSFile.getMetadata().get("prospectoId").toString());
            documento.setNombre(gridFSFile.getFilename());
            documento.setTipoArchivo(gridFSFile.getMetadata().get("_contentType").toString());
            documento.setTamanoArchivo(gridFSFile.getMetadata().get("tamanoArchivo").toString());

            documentos.add(documento);
        }

        return documentos;
    }

    public void eliminarDocumento(String id) {
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
    }
}