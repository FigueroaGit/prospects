package com.concredito.prospects.service;

import com.concredito.prospects.model.Promotor;
import com.concredito.prospects.model.Prospecto;
import com.concredito.prospects.repository.PromotorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotorService {

    private final PromotorRepository promotorRepository;

    @Autowired
    public PromotorService(PromotorRepository promotorRepository) {
        this.promotorRepository = promotorRepository;
    }

    public List<Promotor> getAllPromotores() {
        return promotorRepository.findAll();
    }

    public Optional<Promotor> getPromotorById(String id) {
        return promotorRepository.findById(id);
    }

    public Promotor createPromotor(Promotor promotor) {
        return promotorRepository.save(promotor);
    }

    public Promotor updatePromotor(Promotor promotor) {
        return promotorRepository.save(promotor);
    }

    public void deletePromotorById(String id) {
        promotorRepository.deleteById(id);
    }

    public Promotor login(String usuario, String contrasena) {
        return promotorRepository.findByUsuarioAndContrasena(usuario, contrasena);
    }

}