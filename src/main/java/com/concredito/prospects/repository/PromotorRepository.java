package com.concredito.prospects.repository;

import com.concredito.prospects.model.Promotor;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PromotorRepository extends MongoRepository<Promotor, String> {
    Promotor findByUsuarioAndContrasena(String usuario, String contrasena);
}