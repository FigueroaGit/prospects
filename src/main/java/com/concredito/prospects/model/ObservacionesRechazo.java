package com.concredito.prospects.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "observacionesRechazo")
public class ObservacionesRechazo {

    @Id
    private String id;
    private String observaciones;

    // Relación con Prospecto
    private String prospectoId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getProspectoId() {
        return prospectoId;
    }

    public void setProspectoId(String prospectoId) {
        this.prospectoId = prospectoId;
    }
}