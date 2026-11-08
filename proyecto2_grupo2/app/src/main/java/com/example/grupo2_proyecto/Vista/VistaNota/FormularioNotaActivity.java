package com.example.grupo2_proyecto.Vista.VistaNota;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo2_proyecto.Controlador.ControladorNota;
import com.example.grupo2_proyecto.Modelo.Perfil;
import com.example.grupo2_proyecto.R;

import java.util.Calendar;

public class FormularioNotaActivity extends AppCompatActivity {

    private EditText etDescripcionNota, etFechaActividad, etHoraNota;
    private Button btnGuardarNota, btnCancelar;
    private Perfil perfilActivo;
    private ControladorNota controladorNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        // 🔹 Obtener el perfil activo desde el Intent
        perfilActivo = (Perfil) getIntent().getSerializableExtra("perfilSeleccionado");

        if (perfilActivo == null) {
            Toast.makeText(this, "Error: No se encontró el perfil activo.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 🔹 Inicializar controlador de notas para este perfil
        controladorNota = new ControladorNota(this, perfilActivo.getNombre());

        // 🔹 Inicializar vistas
        etDescripcionNota = findViewById(R.id.etNotaSalud);
        etFechaActividad = findViewById(R.id.etFechaActividad);
        etHoraNota = findViewById(R.id.etHoraNota);
        btnGuardarNota = findViewById(R.id.btnGuardarNota);
        btnCancelar = findViewById(R.id.btnCancelarac);

        // 🔹 Configurar botones
        btnGuardarNota.setOnClickListener(v -> guardarNota());
        btnCancelar.setOnClickListener(v -> finish());

        // 🔹 Configurar selección de fecha
        etFechaActividad.setOnClickListener(v -> mostrarSelectorFecha());

        // 🔹 Configurar selección de hora
        etHoraNota.setOnClickListener(v -> mostrarSelectorHora());
    }

    /**
     * Método para mostrar un DatePickerDialog y seleccionar la fecha.
     */
    private void mostrarSelectorFecha() {
        Calendar calendario = Calendar.getInstance();
        int año = calendario.get(Calendar.YEAR);
        int mes = calendario.get(Calendar.MONTH);
        int dia = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePicker = new DatePickerDialog(this, (view, añoSeleccionado, mesSeleccionado, diaSeleccionado) -> {
            String fechaSeleccionada = String.format("%02d/%02d/%04d", diaSeleccionado, mesSeleccionado + 1, añoSeleccionado);
            etFechaActividad.setText(fechaSeleccionada);
        }, año, mes, dia);

        datePicker.show();
    }

    /**
     * Método para mostrar un TimePickerDialog y seleccionar la hora.
     */
    private void mostrarSelectorHora() {
        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(this, (view, horaSeleccionada, minutoSeleccionado) -> {
            String horaSeleccionadaFormato = String.format("%02d:%02d", horaSeleccionada, minutoSeleccionado);
            etHoraNota.setText(horaSeleccionadaFormato);
        }, hora, minuto, true);

        timePicker.show();
    }

    /**
     * Método para guardar una nueva nota y enviarla a `AdministrarNotasActivity`.
     */
    private void guardarNota() {
        String descripcion = etDescripcionNota.getText().toString().trim();
        String fecha = etFechaActividad.getText().toString().trim();
        String hora = etHoraNota.getText().toString().trim();

        if (descripcion.isEmpty() || fecha.isEmpty() || hora.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 🔹 Agregar la nota al perfil y guardarla
        String notaCompleta = descripcion + " - Fecha: " + fecha + " - Hora: " + hora;
        perfilActivo.agregarNota(notaCompleta, this);
        controladorNota.agregarNota(notaCompleta);

        // 🔹 Enviar la nota a `AdministrarNotasActivity`
        Intent resultIntent = new Intent();
        resultIntent.putExtra("nuevaNota", notaCompleta);
        setResult(RESULT_OK, resultIntent);

        Toast.makeText(this, "Nota guardada correctamente.", Toast.LENGTH_SHORT).show();
        finish();
    }
}

