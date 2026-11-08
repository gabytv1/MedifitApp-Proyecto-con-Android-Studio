package com.example.grupo2_proyecto.Vista.VistaActividadFisica;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo2_proyecto.Controlador.ControladorActividadFisica;
import com.example.grupo2_proyecto.Modelo.ActividadFisica;
import com.example.grupo2_proyecto.Modelo.Perfil;
import com.example.grupo2_proyecto.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegistrarActividadActivity extends AppCompatActivity {

    private EditText etFechaActividad, etDuracion;
    private Spinner spActividadFisica, spHorario;
    private Button btnRegistrarActividad;
    private ControladorActividadFisica controladorActividadFisica;
    private Perfil perfilActivo; // Guardar el perfil activo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_actividad);

        // Obtener el perfil activo desde el Intent
        perfilActivo = (Perfil) getIntent().getSerializableExtra("perfilSeleccionado");

        if (perfilActivo == null) {
            Toast.makeText(this, "Error: No se encontró el perfil activo.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Inicializar controlador con el perfil activo
        controladorActividadFisica = new ControladorActividadFisica(this, perfilActivo.getNombre());

        // Inicializar vistas
        etFechaActividad = findViewById(R.id.etFechaActividad);
        etDuracion = findViewById(R.id.etDuracion);
        spActividadFisica = findViewById(R.id.spActividadFisica);
        spHorario = findViewById(R.id.spHorario);
        btnRegistrarActividad = findViewById(R.id.btnRegistrarActividad);

        cargarSpinners();
        etFechaActividad.setOnClickListener(v -> seleccionarFecha());

        btnRegistrarActividad.setOnClickListener(v -> registrarActividad());
    }

    private void cargarSpinners() {
        String[] actividades = {"Caminar", "Trotar", "Correr", "Funcional", "Crossfit", "Entrenamiento de pesas", "Nadar"};
        ArrayAdapter<String> adapterActividad = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, actividades);
        spActividadFisica.setAdapter(adapterActividad);

        String[] horarios = {"Mañana", "Tarde", "Noche"};
        ArrayAdapter<String> adapterHorario = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, horarios);
        spHorario.setAdapter(adapterHorario);
    }

    private void seleccionarFecha() {
        Calendar calendar = Calendar.getInstance();
        int año = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            Calendar fechaSeleccionada = Calendar.getInstance();
            fechaSeleccionada.set(year, month, dayOfMonth);

            if (fechaSeleccionada.after(Calendar.getInstance())) {
                Toast.makeText(this, "No puedes seleccionar una fecha futura", Toast.LENGTH_SHORT).show();
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                etFechaActividad.setText(sdf.format(fechaSeleccionada.getTime()));
            }
        }, año, mes, dia);

        datePickerDialog.show();
    }

    private void registrarActividad() {
        try {
            String fechaTexto = etFechaActividad.getText().toString().trim();
            String actividad = spActividadFisica.getSelectedItem().toString();
            String horario = spHorario.getSelectedItem().toString();
            String duracionStr = etDuracion.getText().toString().trim();

            if (fechaTexto.isEmpty() || duracionStr.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            Date fechaActividad = sdf.parse(fechaTexto);
            int duracion = Integer.parseInt(duracionStr);

            // 🔹 Crear nueva actividad con el perfil asociado
            ActividadFisica nuevaActividad = new ActividadFisica(perfilActivo.getNombre(), fechaActividad, actividad, duracion, horario);

            // 🔹 Agregar actividad al perfil y al controlador
            perfilActivo.agregarActividadFisica(nuevaActividad);
            controladorActividadFisica.agregarActividad(perfilActivo.getNombre(), fechaActividad, actividad, duracion, horario);

            Toast.makeText(this, "✅ Actividad guardada en tiempo real", Toast.LENGTH_SHORT).show();

            // 🔹 Enviar el perfil actualizado y cerrar sin necesidad de reiniciar la lista
            Intent intent = new Intent();
            intent.putExtra("perfilSeleccionado", perfilActivo);
            setResult(RESULT_OK, intent);
            finish();

        } catch (Exception e) {
            Log.e("RegistrarActividad", "⚠️ Error al registrar actividad", e);
            Toast.makeText(this, "⚠️ Error inesperado.", Toast.LENGTH_SHORT).show();
        }
    }



}





