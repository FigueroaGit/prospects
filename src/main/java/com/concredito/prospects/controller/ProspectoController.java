package com.concredito.prospects.controller;

import com.concredito.prospects.model.Prospecto;
import com.concredito.prospects.service.ProspectoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prospectos")
public class ProspectoController {

    private final ProspectoService prospectoService;

    @Autowired
    public ProspectoController(ProspectoService prospectoService) {
        this.prospectoService = prospectoService;
    }

    @GetMapping
    public List<Prospecto> getAllProspectos() {
        return prospectoService.getAllProspectos();
    }

    @GetMapping("/byPromotor/{idPromotor}")
    public List<Prospecto> getProspectosByPromotorId(@PathVariable String idPromotor) {
        return prospectoService.getProspectosByPromotorId(idPromotor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prospecto> getProspectoById(@PathVariable String id) {
        return prospectoService.getProspectoById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Prospecto> createProspecto(@RequestBody Prospecto prospecto) {
        Prospecto nuevoProspecto = prospectoService.saveProspecto(prospecto);
        return ResponseEntity.ok(nuevoProspecto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prospecto> updateProspecto(@PathVariable String id, @RequestBody Prospecto prospecto) {
        if (!id.equals(prospecto.getId())) {
            return ResponseEntity.badRequest().build();
        }
        Prospecto prospectoActualizado = prospectoService.saveProspecto(prospecto);
        return ResponseEntity.ok(prospectoActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProspecto(@PathVariable String id) {
        prospectoService.deleteProspectoById(id);
        return ResponseEntity.ok().build();
    }
}