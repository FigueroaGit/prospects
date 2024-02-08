package com.concredito.prospects.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "documentos")
public class Documento {

    private String prospectoId;

    private String nombre;

    private String tipoArchivo;

    private String tamanoArchivo;

    private byte[] archivo;

    public Documento() {
    }

    public String getProspectoId() {
        return prospectoId;
    }

    public void setProspectoId(String prospectoId) {
        this.prospectoId = prospectoId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public String getTamanoArchivo() {
        return tamanoArchivo;
    }

    public void setTamanoArchivo(String tamanoArchivo) {
        this.tamanoArchivo = tamanoArchivo;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }
}