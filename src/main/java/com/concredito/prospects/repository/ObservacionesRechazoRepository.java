package com.concredito.prospects.repository;

import com.concredito.prospects.model.ObservacionesRechazo;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ObservacionesRechazoRepository extends MongoRepository<ObservacionesRechazo, String> {
    // Puedes agregar métodos adicionales si es necesario
    List<ObservacionesRechazo> findByProspectoId(String prospectoId);
}