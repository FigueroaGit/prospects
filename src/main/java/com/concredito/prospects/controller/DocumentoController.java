package com.concredito.prospects.controller;

import com.concredito.prospects.model.Documento;
import com.concredito.prospects.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/documentos")
public class DocumentoController {

    private final DocumentoService documentoService;

    @Autowired
    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<Documento> guardarDocumento(
            @RequestParam("nombre") String nombre,
            @RequestParam("prospectoId") String prospectoId,
            @RequestParam("archivo") MultipartFile archivo,
            @RequestParam("tipoArchivo") String tipoArchivo,
            @RequestParam("metadata") String metadata) {
        try {
            Documento documento = documentoService.guardarDocumento(prospectoId, nombre, archivo, tipoArchivo, metadata);
            return new ResponseEntity<>(documento, HttpStatus.CREATED);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/porProspecto/{prospectoId}")
    public ResponseEntity<List<Documento>> obtenerDocumentosPorProspectoId(@PathVariable String prospectoId) {
        List<Documento> documentos = documentoService.obtenerDocumentosPorProspectoId(prospectoId);
        return new ResponseEntity<>(documentos, HttpStatus.OK);
    }

    @GetMapping("/descargar/{documentoId}")
    public ResponseEntity<InputStreamResource> descargarDocumento(@PathVariable String documentoId) {
        try {
            InputStream archivoStream = documentoService.descargarDocumento(documentoId);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", documentoId);

            return new ResponseEntity<>(new InputStreamResource(archivoStream), headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}