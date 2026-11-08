package com.example.grupo2_proyecto.Controlador;

import android.content.Context;
import android.util.Log;
import com.example.grupo2_proyecto.Modelo.ActividadFisica;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class ControladorActividadFisica {
    private String archivoActividades;
    private Context context;
    private List<ActividadFisica> actividades;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    // Constructor: Cada perfil tiene su propio archivo de actividades
    public ControladorActividadFisica(Context context, String nombrePerfil) {
        this.context = context;
        this.archivoActividades = nombrePerfil + "_actividades_fisicas.dat"; // Archivo único por perfil
        this.actividades = cargarActividades(); // Cargar actividades desde el archivo
    }

    // ✅ Guardar actividad inmediatamente al agregarla
    public void agregarActividad(String perfil, Date fecha, String actividad, int duracion, String horario) {
        ActividadFisica nuevaActividad = new ActividadFisica(perfil, fecha, actividad, duracion, horario);
        actividades.add(nuevaActividad);
        guardarActividades(); //  Guardar inmediatamente después de agregar
    }

    // 🔹 Obtener actividades desde el almacenamiento interno
    public List<ActividadFisica> obtenerListadoActividades() {
        actividades = cargarActividades(); // Siempre cargar desde el archivo
        actividades.sort((a1, a2) -> a2.getFecha().compareTo(a1.getFecha())); // Ordenar por fecha
        return actividades;
    }

    // ✅ Eliminar actividad y guardar cambios en el momento
    public boolean eliminarActividad(String actividadNombre) {
        boolean eliminada = actividades.removeIf(actividad -> actividad.getActividad().equalsIgnoreCase(actividadNombre));
        if (eliminada) {
            guardarActividades(); // Guardar inmediatamente después de eliminar
        }
        return eliminada;
    }

    // 🔹 Guardar actividades en el almacenamiento interno
    private synchronized void guardarActividades() {
        try (FileOutputStream fos = context.openFileOutput(archivoActividades, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(actividades);
            Log.d("ControladorActividadFisica", "Actividades guardadas en tiempo real.");
        } catch (IOException e) {
            Log.e("ControladorActividadFisica", "Error al guardar actividades.", e);
        }
    }

    // 🔹 Cargar actividades desde almacenamiento interno
    private List<ActividadFisica> cargarActividades() {
        try (FileInputStream fis = context.openFileInput(archivoActividades);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (List<ActividadFisica>) ois.readObject();
        } catch (FileNotFoundException e) {
            Log.d("ControladorActividadFisica", "No hay actividades almacenadas para este perfil.");
        } catch (IOException | ClassNotFoundException e) {
            Log.e("ControladorActividadFisica", "⚠Error al cargar actividades.", e);
        }
        return new ArrayList<>();
    }
}





