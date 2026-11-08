package com.example.grupo2_proyecto.Controlador;

import android.content.Context;
import android.util.Log;

import com.example.grupo2_proyecto.Modelo.Medico;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ControladorMedico {

    private Context context;
    private String nombrePerfilActivo;  // Nombre del perfil activo
    private List<Medico> listaMedicos;

    // Constructor
    public ControladorMedico(Context context, String nombrePerfilActivo) {
        this.context = context;
        this.nombrePerfilActivo = nombrePerfilActivo;
        this.listaMedicos = cargarMedicos();  // Cargar médicos para el perfil activo
        Log.d("ControladorMedico", "ControladorMedico iniciado para el perfil: " + nombrePerfilActivo);
    }

    // Método para agregar un médico al archivo específico del perfil
    public void agregarMedico(String nombre, String especialidad, String telefono, String email, String direccion) {
        Medico medico = new Medico(nombre, especialidad, telefono, email, direccion);
        listaMedicos.add(medico);
        Log.d("ControladorMedico", "Médico agregado: " + medico.toString());
        guardarMedicos();  // Guardar médicos para este perfil
    }

    // Guardar los médicos en un archivo específico del perfil
    private void guardarMedicos() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                context.openFileOutput(nombrePerfilActivo + "_medicos.dat", Context.MODE_PRIVATE))) {
            oos.writeObject(listaMedicos);
            Log.d("ControladorMedico", "Médicos guardados correctamente en el archivo: " + nombrePerfilActivo + "_medicos.dat");
        } catch (IOException e) {
            Log.e("ControladorMedico", "Error al guardar los médicos", e);
        }
    }

    // Cargar los médicos desde el archivo del perfil activo
    private List<Medico> cargarMedicos() {
        List<Medico> medicos = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(
                context.openFileInput(nombrePerfilActivo + "_medicos.dat"))) {
            medicos = (List<Medico>) ois.readObject();
            Log.d("ControladorMedico", "Médicos cargados correctamente para el perfil: " + nombrePerfilActivo);
        } catch (FileNotFoundException e) {
            // Si no existe el archivo (primer uso), devuelve una lista vacía
            Log.d("ControladorMedico", "No se encontró archivo de médicos para el perfil: " + nombrePerfilActivo);
        } catch (IOException | ClassNotFoundException e) {
            Log.e("ControladorMedico", "Error al cargar los médicos", e);
        }
        return medicos;
    }

    // Obtener la lista de médicos para el perfil activo
    public List<String> obtenerMedicos() {
        List<String> medicosStr = new ArrayList<>();
        for (Medico medico : listaMedicos) {
            medicosStr.add(medico.toString());
        }
        return medicosStr;
    }
    public List<Medico> obtenerMedicosObjetos() {
        return listaMedicos;
    }
}


