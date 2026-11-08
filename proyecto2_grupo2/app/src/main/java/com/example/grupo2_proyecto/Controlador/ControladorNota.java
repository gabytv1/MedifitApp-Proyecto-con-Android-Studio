package com.example.grupo2_proyecto.Controlador;

import android.content.Context;
import android.util.Log;

import com.example.grupo2_proyecto.Modelo.Nota;

import java.io.*;
import java.util.*;

public class ControladorNota {
    private String archivoNotas;
    private Context context;
    private List<Nota> notas;

    // 🔹 Constructor: Cada perfil tiene su propio archivo de notas
    public ControladorNota(Context context, String nombrePerfil) {
        this.context = context;
        this.archivoNotas = nombrePerfil + "_notas.dat"; // Archivo específico por perfil
        this.notas = cargarNotas(); // Cargar notas al inicializar

        // 📌 Asegurar que la lista no sea null después de la carga
        if (this.notas == null) {
            this.notas = new ArrayList<>();
        }
    }

    // 🔹 Obtener el listado de notas ordenado por fecha (más recientes primero)
    public List<Nota> obtenerNotas() {
        if (!notas.isEmpty()) {
            notas.sort((n1, n2) -> n2.getFechaHora().compareTo(n1.getFechaHora())); // Orden descendente por fecha
        }
        return new ArrayList<>(notas); // Devolver una copia para evitar modificaciones externas
    }

    // 🔹 Agregar una nueva nota y guardarla en el archivo
    public void agregarNota(String descripcion) {
        Nota nuevaNota = new Nota(descripcion);
        notas.add(nuevaNota);
        guardarNotas(); // Guardar automáticamente cada vez que se agrega una nota
    }

    // 🔹 Eliminar una nota específica por descripción
    public boolean eliminarNota(String descripcion) {
        boolean eliminado = notas.removeIf(nota -> nota.getDescripcion().equalsIgnoreCase(descripcion));
        if (eliminado) {
            guardarNotas(); // Guardar los cambios
        }
        return eliminado;
    }

    // 🔹 Guardar las notas en un archivo específico del perfil
    public synchronized void guardarNotas() {
        try (FileOutputStream fos = context.openFileOutput(archivoNotas, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(notas);
            Log.d("ControladorNota", "Notas guardadas correctamente en: " + archivoNotas);
        } catch (IOException e) {
            Log.e("ControladorNota", "Error al guardar notas.", e);
        }
    }

    // 🔹 Cargar las notas desde el archivo del perfil
    private List<Nota> cargarNotas() {
        try (FileInputStream fis = context.openFileInput(archivoNotas);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            List<Nota> notasCargadas = (List<Nota>) ois.readObject();
            return notasCargadas != null ? notasCargadas : new ArrayList<>();
        } catch (FileNotFoundException e) {
            Log.d("ControladorNota", "No hay notas almacenadas para este perfil: " + archivoNotas);
        } catch (IOException | ClassNotFoundException e) {
            Log.e("ControladorNota", "Error al cargar notas.", e);
        }
        return new ArrayList<>();
    }
}


