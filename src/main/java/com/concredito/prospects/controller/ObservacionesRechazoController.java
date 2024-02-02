package com.concredito.prospects.controller;

import com.concredito.prospects.model.ObservacionesRechazo;
import com.concredito.prospects.service.ObservacionesRechazoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/observaciones-rechazo")
public class ObservacionesRechazoController {

    private final ObservacionesRechazoService observacionesRechazoService;

    @Autowired
    public ObservacionesRechazoController(ObservacionesRechazoService observacionesRechazoService) {
        this.observacionesRechazoService = observacionesRechazoService;
    }

    @GetMapping
    public List<ObservacionesRechazo> getAllObservacionesRechazo() {
        return observacionesRechazoService.getAllObservacionesRechazo();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ObservacionesRechazo> getObservacionRechazoById(@PathVariable String id) {
        return observacionesRechazoService.getObservacionesRechazoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/prospecto/{prospectoId}")
    public ResponseEntity<List<ObservacionesRechazo>> getObservacionesRechazoByProspectoId(@PathVariable String prospectoId) {
        List<ObservacionesRechazo> observacionesRechazo = observacionesRechazoService.getObservacionesRechazoByProspectoId(prospectoId);
        return ResponseEntity.ok(observacionesRechazo);
    }

    @PostMapping
    public ResponseEntity<ObservacionesRechazo> addObservacionesRechazo(@RequestBody ObservacionesRechazo observacionesRechazo) {
        ObservacionesRechazo nuevaObservacion = observacionesRechazoService.addObservacionesRechazo(observacionesRechazo);
        return ResponseEntity.ok(nuevaObservacion);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ObservacionesRechazo> updateObservacionRechazo(@PathVariable String id,
                                                                       @RequestBody ObservacionesRechazo observacionRechazo) {
        if (!id.equals(observacionRechazo.getId())) {
            return ResponseEntity.badRequest().build();
        }
        ObservacionesRechazo observacionActualizada = observacionesRechazoService.updateObservacionesRechazo(observacionRechazo);
        return ResponseEntity.ok(observacionActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteObservacionesRechazo(@PathVariable String id) {
        observacionesRechazoService.deleteObservacionesRechazoById(id);
        return ResponseEntity.ok().build();
    }
}