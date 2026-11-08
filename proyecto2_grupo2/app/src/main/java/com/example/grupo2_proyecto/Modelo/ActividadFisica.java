package com.example.grupo2_proyecto.Modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ActividadFisica implements Serializable {
    private static final long serialVersionUID = 1L; // Versión de serialización

    private String perfilAsociado;
    private Date fecha;
    private String actividad;
    private int duracion;
    private String horario;
    private transient SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    // Constructor
    public ActividadFisica(String perfilAsociado, Date fecha, String actividad, int duracion, String horario) {
        this.perfilAsociado = perfilAsociado;
        this.fecha = fecha;
        this.actividad = actividad;
        this.duracion = duracion;
        this.horario = horario;
    }

    // Getters
    public String getPerfilAsociado() {
        return perfilAsociado;
    }

    public Date getFecha() {
        return fecha;
    }

    public String getActividad() {
        return actividad;
    }

    public int getDuracion() {
        return duracion;
    }

    public String getHorario() {
        return horario;
    }

    // Método toString para mostrar la información de manera legible
    @Override
    public String toString() {
        return "Perfil: " + perfilAsociado + ", Fecha: " + dateFormat.format(fecha) +
                ", Actividad: " + actividad + ", Duración: " + duracion + " min, Horario: " + horario;
    }
}
