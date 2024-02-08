package com.concredito.prospects.controller;

import com.concredito.prospects.model.Documento;
import com.concredito.prospects.service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/documentos")
public class DocumentoController {

    private final DocumentoService documentoService;

    @Autowired
    public DocumentoController(DocumentoService documentoService) {
        this.documentoService = documentoService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("archivo")MultipartFile archivo, @RequestParam("nombre") String nombre, @RequestParam("prospectoId") String prospectoId) throws IOException {
        return new ResponseEntity<>(documentoService.agregarArchivo(archivo, nombre, prospectoId), HttpStatus.OK);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        Documento cargarDocumento = documentoService.descargarArchivo(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(cargarDocumento.getTipoArchivo() ))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + cargarDocumento.getNombre() + "\"")
                .body(new ByteArrayResource(cargarDocumento.getArchivo()));
    }

    @GetMapping("/documentos-por-prospecto")
    public ResponseEntity<List<Documento>> obtenerDocumentosPorProspecto(@RequestParam("prospectoId") String prospectoId) {
        try {
            List<Documento> documentos = documentoService.obtenerDocumentosPorProspectoId(prospectoId);
            return ResponseEntity.ok(documentos);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @DeleteMapping("/eliminar-documento")
    public ResponseEntity<String> eliminarDocumento(@RequestParam("documentoId") String documentoId) {
        try {
            documentoService.eliminarDocumento(documentoId);
            return ResponseEntity.ok("Documento eliminado correctamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el documento: " + e.getMessage());
        }
    }
}