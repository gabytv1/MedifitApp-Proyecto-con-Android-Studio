package com.example.grupo2_proyecto.Controlador;

import android.content.Context;
import android.util.Log;

import com.example.grupo2_proyecto.Modelo.CitaMedica;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ControladorCitaMedica {
    private String archivoCitas;
    private Context context;
    private List<CitaMedica> citas;

    // 🔹 Cada perfil tiene su propio archivo de citas
    public ControladorCitaMedica(Context context, String nombrePerfil) {
        this.context = context;
        this.archivoCitas = nombrePerfil + "_citas_medicas.dat"; // Archivo único por perfil
        this.citas = cargarCitas();
    }

    // 🔹 Obtener la lista de citas del perfil
    public List<CitaMedica> obtenerCitas() {
        return new ArrayList<>(citas); // Devuelve una copia para evitar modificaciones externas
    }

    // 🔹 Agregar una nueva cita y guardarla en el archivo
    public void agregarCita(CitaMedica nuevaCita) {
        citas.add(nuevaCita);
        guardarCitas(); // Guardar automáticamente al agregar
    }

    // 🔹 Guardar las citas en un archivo específico del perfil
    private void guardarCitas() {
        try (FileOutputStream fos = context.openFileOutput(archivoCitas, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(citas);
            Log.d("ControladorCitaMedica", "Citas guardadas correctamente.");
        } catch (IOException e) {
            Log.e("ControladorCitaMedica", "Error al guardar citas.", e);
        }
    }

    // 🔹 Cargar las citas desde el archivo del perfil
    private List<CitaMedica> cargarCitas() {
        try (FileInputStream fis = context.openFileInput(archivoCitas);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (List<CitaMedica>) ois.readObject();
        } catch (FileNotFoundException e) {
            Log.d("ControladorCitaMedica", "No hay citas almacenadas.");
        } catch (IOException | ClassNotFoundException e) {
            Log.e("ControladorCitaMedica", "Error al cargar citas.", e);
        }
        return new ArrayList<>();
    }
}



