package com.concredito.prospects.controller;

import com.concredito.prospects.model.Promotor;
import com.concredito.prospects.model.Prospecto;
import com.concredito.prospects.service.PromotorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/promotores")
public class PromotorController {

    private final PromotorService promotorService;

    @Autowired
    public PromotorController(PromotorService promotorService) {
        this.promotorService = promotorService;
    }

    @GetMapping
    public ResponseEntity<List<Promotor>> getAllPromotores() {
        List<Promotor> promotores = promotorService.getAllPromotores();
        return ResponseEntity.ok(promotores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promotor> getPromotorById(@PathVariable String id) {
        return promotorService.getPromotorById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Promotor> createPromotor(@RequestBody Promotor promotor) {
        Promotor nuevoPromotor = promotorService.createPromotor(promotor);
        return ResponseEntity.ok(nuevoPromotor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Promotor> updatePromotor(@PathVariable String id, @RequestBody Promotor promotor) {
        if (!id.equals(promotor.getId())) {
            return ResponseEntity.badRequest().build();
        }
        Promotor promotorActualizado = promotorService.updatePromotor(promotor);
        return ResponseEntity.ok(promotorActualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotor(@PathVariable String id) {
        promotorService.deletePromotorById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String usuario, @RequestParam String contrasena) {
        Promotor promotorLogueado = promotorService.login(usuario, contrasena);

        if (promotorLogueado != null) {
            // Inicio de sesión exitoso
            return ResponseEntity.ok(promotorLogueado);
        } else {
            // Credenciales inválidas
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}