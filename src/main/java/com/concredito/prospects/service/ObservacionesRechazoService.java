package com.concredito.prospects.service;

import com.concredito.prospects.model.ObservacionesRechazo;
import com.concredito.prospects.repository.ObservacionesRechazoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ObservacionesRechazoService {

    private final ObservacionesRechazoRepository observacionesRechazoRepository;

    @Autowired
    public ObservacionesRechazoService(ObservacionesRechazoRepository observacionesRechazoRepository) {
        this.observacionesRechazoRepository = observacionesRechazoRepository;
    }

    public List<ObservacionesRechazo> getAllObservacionesRechazo() {
        return observacionesRechazoRepository.findAll();
    }

    public Optional<ObservacionesRechazo> getObservacionesRechazoById(String id) {
        return observacionesRechazoRepository.findById(id);
    }

    public ObservacionesRechazo addObservacionesRechazo(ObservacionesRechazo observacionesRechazo) {
        observacionesRechazo.setProspectoId(observacionesRechazo.getProspectoId());
        return observacionesRechazoRepository.save(observacionesRechazo);
    }

    public ObservacionesRechazo updateObservacionesRechazo(ObservacionesRechazo observacionesRechazo) {
        return observacionesRechazoRepository.save(observacionesRechazo);
    }

    public void deleteObservacionesRechazoById(String id) {
        observacionesRechazoRepository.deleteById(id);
    }

    // Método adicional

    public List<ObservacionesRechazo> getObservacionesRechazoByProspectoId(String prospectoId) {
        return observacionesRechazoRepository.findByProspectoId(prospectoId);
    }
}