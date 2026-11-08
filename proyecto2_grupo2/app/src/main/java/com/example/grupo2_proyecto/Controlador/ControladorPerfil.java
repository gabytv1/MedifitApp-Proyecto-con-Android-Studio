package com.example.grupo2_proyecto.Controlador;

import android.content.Context;
import android.util.Log;

import com.example.grupo2_proyecto.Modelo.Perfil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class ControladorPerfil {
    private Context context;
    private ArrayList<Perfil> l_perfil;

    public ControladorPerfil(Context context) {
        this.context = context;
        this.l_perfil = cargarPerfiles(); // Cargar perfiles desde archivos individuales
    }

    // Agregar un perfil
    public void agregarPerfil(Perfil perfil) {
        if (!perfilExiste(perfil.getNombre())) {
            guardarPerfilEnArchivo(perfil); // Guardar como archivo individual
            l_perfil.add(perfil); // Actualizar la lista en memoria
        } else {
            Log.d("ControladorPerfil", "El perfil ya existe: " + perfil.getNombre());
        }
    }

    // Eliminar un perfil
    public void eliminarPerfil(Perfil perfil) {
        File file = new File(context.getFilesDir(), perfil.getNombre() + ".dat");
        if (file.exists() && file.delete()) {
            l_perfil.remove(perfil); // Actualizar la lista en memoria
            Log.d("ControladorPerfil", "Perfil eliminado: " + perfil.getNombre());
        } else {
            Log.d("ControladorPerfil", "No se pudo eliminar el perfil: " + perfil.getNombre());
        }
    }

    // Guardar un perfil como archivo individual
    private void guardarPerfilEnArchivo(Perfil perfil) {
        try {
            FileOutputStream fos = context.openFileOutput(perfil.getNombre() + ".dat", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(perfil);
            oos.close();
            fos.close();
            Log.d("ControladorPerfil", "Perfil guardado: " + perfil.getNombre());
        } catch (Exception e) {
            Log.e("ControladorPerfil", "Error al guardar el perfil", e);
        }
    }

    // Cargar todos los perfiles desde archivos individuales
    private ArrayList<Perfil> cargarPerfiles() {
        ArrayList<Perfil> perfiles = new ArrayList<>();
        File directory = context.getFilesDir();
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".dat")); // Filtrar solo archivos .dat

        if (files != null) {
            for (File file : files) {
                try {
                    FileInputStream fis = new FileInputStream(file);
                    ObjectInputStream ois = new ObjectInputStream(fis);
                    Perfil perfil = (Perfil) ois.readObject();
                    perfiles.add(perfil);
                    ois.close();
                    fis.close();
                } catch (Exception e) {
                    Log.e("ControladorPerfil", "Error al cargar el perfil desde: " + file.getName(), e);
                }
            }
        }
        return perfiles;
    }

    // Comprobar si un perfil ya existe
    private boolean perfilExiste(String nombre) {
        File file = new File(context.getFilesDir(), nombre + ".dat");
        return file.exists();
    }

    // Obtener la lista de perfiles
    public ArrayList<Perfil> obtenerPerfiles() {
        return l_perfil;
    }

    // Método para actualizar un perfil
    public void actualizarPerfil(Perfil perfil) {
        guardarPerfilEnArchivo(perfil); // Guarda el perfil actualizado
    }
}





