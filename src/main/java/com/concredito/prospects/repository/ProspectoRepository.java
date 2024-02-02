package com.concredito.prospects.repository;


import com.concredito.prospects.model.Prospecto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProspectoRepository extends MongoRepository<Prospecto, String> {
    List<Prospecto> findByIdPromotor(String idPromotor);

    // Método para buscar prospectos por estatus
    List<Prospecto> findByEstatus(String estatus);

}
