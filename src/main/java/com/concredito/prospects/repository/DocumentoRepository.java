package com.concredito.prospects.repository;

import com.concredito.prospects.model.Documento;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DocumentoRepository extends MongoRepository<Documento, String> {

    List<Documento> findByProspectoId(String prospectoId);

}