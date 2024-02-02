package com.concredito.prospects.service;

import com.concredito.prospects.model.Prospecto;
import com.concredito.prospects.repository.ProspectoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProspectoService {

    private final ProspectoRepository prospectoRepository;

    @Autowired
    public ProspectoService(ProspectoRepository prospectoRepository) {
        this.prospectoRepository = prospectoRepository;
    }

    // Métodos CRUD básicos

    public List<Prospecto> getAllProspectos() {
        return prospectoRepository.findAll();
    }

    public Optional<Prospecto> getProspectoById(String id) {
        return prospectoRepository.findById(id);
    }

    public Prospecto saveProspecto(Prospecto prospecto) {
        return prospectoRepository.save(prospecto);
    }

    public void deleteProspectoById(String id) {
        prospectoRepository.deleteById(id);
    }

    // Métodos adicionales

    public List<Prospecto> getProspectosByPromotorId(String idPromotor) {
        return prospectoRepository.findByIdPromotor(idPromotor);
    }

    public List<Prospecto> getProspectosByEstatus(String estatus) {
        return prospectoRepository.findByEstatus(estatus);
    }
}
