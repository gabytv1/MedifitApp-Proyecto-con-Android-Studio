package com.example.grupo2_proyecto.Controlador;

import android.content.Context;
import android.util.Log;
import com.example.grupo2_proyecto.Modelo.Medicamento;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ControladorMedicamento {
    private String archivoMedicamentos;
    private Context context;
    private List<Medicamento> medicamentos;

    // 🔹 Constructor: Cada perfil tiene su propio archivo de medicamentos
    public ControladorMedicamento(Context context, String nombrePerfil) {
        this.context = context;
        this.archivoMedicamentos = nombrePerfil + "_medicamentos.dat";
        this.medicamentos = cargarMedicamentos();

        if (this.medicamentos == null) {
            this.medicamentos = new ArrayList<>();
        }
    }

    // 🔹 Obtener lista de medicamentos
    public List<Medicamento> obtenerMedicamentos() {
        return new ArrayList<>(medicamentos);
    }

    // 🔹 Agregar un medicamento y guardar cambios
    public void agregarMedicamento(Medicamento nuevoMedicamento) {
        if (nuevoMedicamento != null) {
            medicamentos.add(nuevoMedicamento);
            guardarMedicamentos();
        }
    }

    // 🔹 ACTUALIZAR MEDICAMENTO (Este método lo agregamos)
    public boolean actualizarMedicamento(String nombre, Medicamento medicamentoActualizado) {
        for (int i = 0; i < medicamentos.size(); i++) {
            if (medicamentos.get(i).getNombre().equalsIgnoreCase(nombre)) {
                medicamentos.set(i, medicamentoActualizado); // Reemplazar por el actualizado
                guardarMedicamentos();
                return true;
            }
        }
        return false;
    }

    // 🔹 Registrar toma de un medicamento correctamente
    public String registrarToma(String nombre) {
        for (Medicamento medicamento : medicamentos) {
            if (medicamento.getNombre().equalsIgnoreCase(nombre)) {
                try {
                    if (medicamento.getCantidad() < medicamento.getDosis()) {
                        return "⚠️ No hay suficiente cantidad para registrar la toma.";
                    }

                    medicamento.registrarToma(this);
                    guardarMedicamentos();
                    return "✅ Toma registrada: " + medicamento.getDosis() + " unidades de " + nombre;
                } catch (IllegalStateException e) {
                    return "⚠️ Error: " + e.getMessage();
                }
            }
        }
        return "⚠️ Error: Medicamento no encontrado.";
    }

    // 🔹 Reprogramar la toma de un medicamento con validación
    public String reprogramarToma(String nombre, String nuevaHora) {
        if (nuevaHora == null || nuevaHora.trim().isEmpty()) {
            return "⚠️ Error: La nueva hora no puede estar vacía.";
        }

        for (Medicamento medicamento : medicamentos) {
            if (medicamento.getNombre().equalsIgnoreCase(nombre)) {
                medicamento.reprogramarToma(nuevaHora,this);
                guardarMedicamentos();
                return "🔄 Toma de " + nombre + " reprogramada para las " + nuevaHora;
            }
        }
        return "⚠️ Error: Medicamento no encontrado.";
    }

    // 🔹 Obtener la última toma de un medicamento
    public String obtenerUltimaToma(String nombre) {
        for (Medicamento medicamento : medicamentos) {
            if (medicamento.getNombre().equalsIgnoreCase(nombre)) {
                return medicamento.getUltimaToma();
            }
        }
        return "⚠️ No se encontró el medicamento.";
    }

    // 🔹 Eliminar medicamento por nombre
    public boolean eliminarMedicamento(String nombre) {
        boolean eliminado = medicamentos.removeIf(m -> m.getNombre().equalsIgnoreCase(nombre));
        if (eliminado) {
            guardarMedicamentos();
        }
        return eliminado;
    }

    // 🔹 Guardar medicamentos en archivo
    public synchronized void guardarMedicamentos() {
        if (medicamentos == null) {
            Log.w("ControladorMedicamento", "⚠️ No hay medicamentos para guardar.");
            return;
        }

        try (FileOutputStream fos = context.openFileOutput(archivoMedicamentos, Context.MODE_PRIVATE);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(medicamentos);
            Log.d("ControladorMedicamento", "✅ Medicamentos guardados correctamente.");
        } catch (IOException e) {
            Log.e("ControladorMedicamento", "⚠️ Error al guardar medicamentos.", e);
        }
    }

    // 🔹 Cargar medicamentos desde archivo
    private List<Medicamento> cargarMedicamentos() {
        try (FileInputStream fis = context.openFileInput(archivoMedicamentos);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (List<Medicamento>) ois.readObject();
        } catch (FileNotFoundException e) {
            Log.d("ControladorMedicamento", "⚠️ No hay medicamentos almacenados.");
        } catch (IOException | ClassNotFoundException e) {
            Log.e("ControladorMedicamento", "⚠️ Error al cargar medicamentos.", e);
        }
        return new ArrayList<>();
    }

    // 🔹 Recargar medicamento y guardar cambios
    public String recargarMedicamento(String nombre, int cantidad) {
        if (cantidad <= 0) {
            return "⚠️ Error: La cantidad a recargar debe ser mayor a 0.";
        }

        for (Medicamento medicamento : medicamentos) {
            if (medicamento.getNombre().equalsIgnoreCase(nombre)) {
                medicamento.recargar(cantidad);
                guardarMedicamentos();
                return "✅ Recarga exitosa. Nuevo stock de " + medicamento.getNombre() + ": " + medicamento.getCantidad();
            }
        }
        return "⚠️ Error: Medicamento no encontrado.";
    }

    // 🔹 Establecer umbral de un medicamento
    public String establecerUmbral(String nombre, int umbral) {
        for (Medicamento medicamento : medicamentos) {
            if (medicamento.getNombre().equalsIgnoreCase(nombre)) {
                medicamento.setUmbral(umbral);
                guardarMedicamentos();
                return "✅ Umbral establecido en " + umbral + " unidades para " + nombre;
            }
        }
        return "⚠️ Error: Medicamento no encontrado.";
    }
}



