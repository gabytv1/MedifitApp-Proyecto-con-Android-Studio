package com.example.grupo2_proyecto.Modelo;

import android.os.Build;
import com.example.grupo2_proyecto.Controlador.ControladorMedicamento;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Medicamento implements Serializable {
    private String nombre;
    private int cantidad;
    private String presentacion;
    private int frecuencia;
    private int frecuenciaXdia;
    private int dosis;
    private int umbral;
    private String hora;
    private ArrayList<String> historialTomas;
    private ArrayList<String> historialRecargas;
    private String ultimaToma;
    private boolean agotado;

    // 🔹 Constructor
    public Medicamento(String nombre, int cantidad, String presentacion, int frecuencia, int frecuenciaXdia, int dosis, String hora) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.presentacion = presentacion;
        this.frecuencia = frecuencia;
        this.frecuenciaXdia = frecuenciaXdia;
        this.dosis = dosis;
        this.hora = hora;
        this.historialTomas = new ArrayList<>();
        this.historialRecargas = new ArrayList<>();
        this.umbral = 0;
        this.ultimaToma = "Nunca tomado";
        this.agotado = cantidad == 0;
    }

    // 🔹 Getters y Setters
    public String getNombre() { return nombre; }
    public int getCantidad() { return cantidad; }
    public String getPresentacion() { return presentacion; }
    public int getFrecuencia() { return frecuencia; }
    public int getFrecuenciaXdia() { return frecuenciaXdia; }
    public String getHora() { return hora; }
    public int getDosis() { return dosis; }
    public int getUmbral() { return umbral; }
    public String getUltimaToma() { return ultimaToma; }
    public boolean isAgotado() { return agotado; }

    public void setHora(String hora) { this.hora = hora; }
    public void setUmbral(int umbral) { this.umbral = umbral; }

    // 🔹 Verificar si el medicamento está por debajo del umbral
    public boolean verificarUmbral() {
        return this.cantidad <= this.umbral;
    }

    // 🔹 Registrar toma de medicamento correctamente
    public void registrarToma(ControladorMedicamento controlador) {
        if (cantidad >= dosis) {
            cantidad -= dosis; // Reducir cantidad según la dosis

            // Si la cantidad llega a 0, marcar como agotado
            if (cantidad <= 0) {
                cantidad = 0;
                agotado = true;
            }

            ultimaToma = obtenerFechaHoraActual();
            historialTomas.add(ultimaToma + ": Se tomó " + dosis + " unidades.");

            // 🔹 Guardar cambios en el controlador
            controlador.actualizarMedicamento(nombre, this);
            controlador.guardarMedicamentos();
        } else {
            throw new IllegalStateException("⚠️ No hay suficiente cantidad para registrar la toma.");
        }
    }

    // 🔹 Omitir la toma sin modificar la cantidad
    public void omitirToma(ControladorMedicamento controlador) {
        String fechaHora = obtenerFechaHoraActual();
        historialTomas.add("❌ Toma omitida: " + fechaHora);

        // 🔹 Guardar cambios en el controlador
        controlador.actualizarMedicamento(nombre, this);
        controlador.guardarMedicamentos();
    }

    // 🔹 Reprogramar la toma asegurando que la nueva hora es válida
    public void reprogramarToma(String nuevaHora, ControladorMedicamento controlador) {
        if (nuevaHora == null || nuevaHora.trim().isEmpty()) {
            throw new IllegalArgumentException("⚠️ La nueva hora no puede estar vacía.");
        }

        this.hora = nuevaHora;
        historialTomas.add("⏳ Toma reprogramada para " + nuevaHora);

        // 🔹 Guardar cambios en el controlador
        controlador.actualizarMedicamento(nombre, this);
        controlador.guardarMedicamentos();
    }

    // 🔹 Recargar medicamento correctamente
    public String recargar(int cantidad) {
        if (cantidad <= 0) {
            return "⚠️ Error: La cantidad a recargar debe ser mayor a 0.";
        }

        this.cantidad += cantidad;
        this.agotado = false;
        historialRecargas.add("🔄 Recargado con " + cantidad + " unidades. Total: " + this.cantidad);
        return "✅ Recarga exitosa. Nuevo stock: " + this.cantidad;
    }

    // 🔹 Obtener la fecha y hora actual en un formato legible
    private String obtenerFechaHoraActual() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime ahora = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm, dd MMM yyyy");
            return ahora.format(formatter);
        } else {
            Calendar ahora = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm, dd MMM yyyy", Locale.getDefault());
            return formatter.format(ahora.getTime());
        }
    }

    // 🔹 Obtener historial de tomas y recargas
    public ArrayList<String> obtenerHistorialTomas() { return historialTomas; }
    public ArrayList<String> obtenerHistorialRecargas() { return historialRecargas; }

    // 🔹 Mostrar la información del medicamento en un formato legible
    @Override
    public String toString() {
        return "Nombre: " + nombre + "\n" +
                "Cantidad: " + cantidad + (agotado ? " (AGOTADO) ⚠️" : "") + "\n" +
                "Presentación: " + presentacion + "\n" +
                "Frecuencia: " + frecuencia + " días a la semana\n" +
                "Frecuencia al día: " + frecuenciaXdia + " veces\n" +
                "Dosis: " + dosis + "\n" +
                "Hora de toma: " + hora + "\n" +
                "Última toma: " + ultimaToma + "\n" +
                (verificarUmbral() ? "⚠️ ¡Este medicamento está por debajo del umbral!" : "");
    }
}




