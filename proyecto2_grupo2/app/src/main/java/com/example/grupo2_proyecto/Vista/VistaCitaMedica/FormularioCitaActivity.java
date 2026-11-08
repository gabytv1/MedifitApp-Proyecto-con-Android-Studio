package com.example.grupo2_proyecto.Vista.VistaCitaMedica;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo2_proyecto.Controlador.ControladorCitaMedica;
import com.example.grupo2_proyecto.Controlador.ControladorMedico;
import com.example.grupo2_proyecto.Modelo.CitaMedica;
import com.example.grupo2_proyecto.Modelo.Medico;
import com.example.grupo2_proyecto.Modelo.Perfil;
import com.example.grupo2_proyecto.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FormularioCitaActivity extends AppCompatActivity {

    private EditText etTituloCita, etFechaHora;
    private Spinner spElegirDoctor;
    private Button btnGuardarCita, btnCancelarCita;
    private ControladorMedico controladorMedico;
    private ControladorCitaMedica controladorCitaMedica;
    private List<Medico> listaMedicos;
    private Perfil perfilActivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cita);

        // Inicializar vistas
        etTituloCita = findViewById(R.id.etTituloCita);
        etFechaHora = findViewById(R.id.etFechaHora);
        spElegirDoctor = findViewById(R.id.spElegirDoctor);
        btnGuardarCita = findViewById(R.id.btnGuardarCita);
        btnCancelarCita = findViewById(R.id.btnCancelarCita);

        // Obtener el perfil activo desde el Intent
        perfilActivo = (Perfil) getIntent().getSerializableExtra("perfilSeleccionado");

        if (perfilActivo == null) {
            Toast.makeText(this, "Error: No se encontró el perfil activo", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Inicializar controladores con el perfil activo
        controladorMedico = new ControladorMedico(this, perfilActivo.getNombre());
        controladorCitaMedica = new ControladorCitaMedica(this, perfilActivo.getNombre());

        // Cargar lista de doctores en el Spinner
        cargarListaDoctores();

        // Configurar selección de fecha y hora
        configurarFechaHora();

        // Configurar botones
        btnGuardarCita.setOnClickListener(v -> guardarCita());
        btnCancelarCita.setOnClickListener(v -> finish());
    }

    private void cargarListaDoctores() {
        listaMedicos = controladorMedico.obtenerMedicosObjetos();
        List<String> nombresDoctores = new ArrayList<>();

        if (!listaMedicos.isEmpty()) {
            for (Medico medico : listaMedicos) {
                nombresDoctores.add(medico.getNombre());
            }
        } else {
            nombresDoctores.add("No hay médicos registrados");
            spElegirDoctor.setEnabled(false);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresDoctores);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spElegirDoctor.setAdapter(adapter);
    }

    private void configurarFechaHora() {
        etFechaHora.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();

            new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                new TimePickerDialog(this, (timeView, hourOfDay, minute) -> {
                    String fechaHora = String.format("%02d/%02d/%04d %02d:%02d", dayOfMonth, month + 1, year, hourOfDay, minute);
                    etFechaHora.setText(fechaHora);
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true).show();
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
        });
    }

    private void guardarCita() {
        String titulo = etTituloCita.getText().toString().trim();
        int posicionSeleccionada = spElegirDoctor.getSelectedItemPosition();

        if (titulo.isEmpty() || posicionSeleccionada == Spinner.INVALID_POSITION || etFechaHora.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (listaMedicos.isEmpty()) {
            Toast.makeText(this, "No hay médicos registrados. Agregue un médico antes de registrar una cita.", Toast.LENGTH_SHORT).show();
            return;
        }

        String fechaHora = etFechaHora.getText().toString().trim();
        Medico doctorSeleccionado = listaMedicos.get(posicionSeleccionada);

        // Crear nueva cita
        CitaMedica nuevaCita = new CitaMedica(titulo, doctorSeleccionado, fechaHora);

        // Agregar la cita al perfil activo y guardarla
        perfilActivo.getCitasMedicas().add(nuevaCita);
        controladorCitaMedica.agregarCita(nuevaCita); // Guardar en el archivo

        // 🔹 Enviar solo la nueva cita en lugar de todo el perfil
        Intent intent = new Intent();
        intent.putExtra("nuevaCita", nuevaCita);
        setResult(RESULT_OK, intent);

        Toast.makeText(this, "Cita guardada correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }



}



