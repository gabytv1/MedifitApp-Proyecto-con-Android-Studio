package com.example.grupo2_proyecto.Modelo;

import java.io.Serializable;

public class CitaMedica implements Serializable {
    private static final long serialVersionUID = 1L;

    private String titulo;
    private Medico medicoNombre; // Ahora almacenamos solo el nombre del médico
    private String fechaHora;

    public CitaMedica(String titulo, Medico medicoNombre, String fechaHora) {
        this.titulo = titulo;
        this.medicoNombre = medicoNombre;
        this.fechaHora = fechaHora;
    }

    // Getters y Setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Medico getMedicoNombre() {
        return medicoNombre;
    }

    public void setMedicoNombre(Medico medicoNombre) {
        this.medicoNombre = medicoNombre;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public String toString() {
        return "Cita: " + titulo + " con " + medicoNombre + " el " + fechaHora;
    }
}


