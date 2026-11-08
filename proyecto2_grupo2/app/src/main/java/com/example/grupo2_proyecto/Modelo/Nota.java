package com.example.grupo2_proyecto.Modelo;

import java.io.Serializable;
import java.util.Date;

public class Nota implements Serializable {
    private Date fechaHora;
    private String descripcion;

    // Constructor
    public Nota(String descripcion) {
        this.fechaHora = new Date(); // Usa `Date` en vez de `LocalDateTime`
        this.descripcion = descripcion;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    @Override
    public String toString() {
        return "Fecha: " + fechaHora + ", Descripción: " + descripcion;
    }
}

