package com.example.grupo2_proyecto.Vista.vistaMedicamento;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo2_proyecto.Controlador.ControladorMedicamento;
import com.example.grupo2_proyecto.Modelo.Medicamento;
import com.example.grupo2_proyecto.Modelo.Perfil;
import com.example.grupo2_proyecto.R;

import java.util.Calendar;

public class FormularioMedicamento extends AppCompatActivity {

    private EditText etNombreMedicamento, etCantidad, etDosis, etHora, etFrecuencia, frecuenciaxDia;
    private Spinner spPresentacion;
    private Button btnRegistrar, btnCancelar;
    private Perfil perfilActivo; // Guardar el perfil activo
    private ControladorMedicamento controladorMedicamento; // Controlador específico del perfil

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_medicamento);

        // 🔹 Obtener el perfil activo desde el Intent
        perfilActivo = (Perfil) getIntent().getSerializableExtra("perfilSeleccionado");

        if (perfilActivo == null) {
            Toast.makeText(this, "Error: No se encontró el perfil activo.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 🔹 Inicializar el controlador de medicamentos con el perfil activo
        controladorMedicamento = new ControladorMedicamento(this, perfilActivo.getNombre());

        // 🔹 Inicializar vistas
        etNombreMedicamento = findViewById(R.id.etNombre);
        etCantidad = findViewById(R.id.etCantidad);
        etDosis = findViewById(R.id.etDosis);
        etFrecuencia = findViewById(R.id.etFrecuencia);
        frecuenciaxDia = findViewById(R.id.frecuenciaxDia);
        spPresentacion = findViewById(R.id.spPresentacion);
        etHora = findViewById(R.id.etHora); // EditText para la hora
        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnCancelar = findViewById(R.id.btnCancelarac);

        // 🔹 Configurar Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.presentacion_medicamentos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPresentacion.setAdapter(adapter);

        // 🔹 Configurar picker de hora
        etHora.setOnClickListener(v -> mostrarSelectorHora());

        // 🔹 Acciones de los botones
        btnRegistrar.setOnClickListener(v -> registrarMedicamento());
        btnCancelar.setOnClickListener(v -> finish());
    }

    /**
     * Método para mostrar un selector de hora (TimePickerDialog).
     */
    private void mostrarSelectorHora() {
        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(this, (view, horaSeleccionada, minutoSeleccionado) -> {
            String horaSeleccionadaFormato = String.format("%02d:%02d", horaSeleccionada, minutoSeleccionado);
            etHora.setText(horaSeleccionadaFormato);
        }, hora, minuto, true);

        timePicker.show();
    }

    /**
     * Método para registrar un medicamento validando los datos ingresados.
     */
    private void registrarMedicamento() {
        String nombre = etNombreMedicamento.getText().toString().trim();
        String cantidadStr = etCantidad.getText().toString().trim();
        String dosisStr = etDosis.getText().toString().trim();
        String frecuenciaStr = etFrecuencia.getText().toString().trim();
        String frecuenciaxdiaStr = frecuenciaxDia.getText().toString().trim();
        String presentacion = spPresentacion.getSelectedItem().toString();
        String hora = etHora.getText().toString().trim();

        // 🔹 Validar que todos los campos están llenos
        if (nombre.isEmpty() || cantidadStr.isEmpty() || dosisStr.isEmpty() ||
                frecuenciaStr.isEmpty() || frecuenciaxdiaStr.isEmpty() || hora.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 🔹 Convertir valores numéricos con control de errores
        int cantidad, dosis, frecuencia, frecuenciaxdia;
        try {
            cantidad = Integer.parseInt(cantidadStr);
            dosis = Integer.parseInt(dosisStr);
            frecuencia = Integer.parseInt(frecuenciaStr);
            frecuenciaxdia = Integer.parseInt(frecuenciaxdiaStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Error: Asegúrese de ingresar números válidos.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 🔹 Crear nuevo medicamento con hora
        Medicamento nuevoMedicamento = new Medicamento(nombre, cantidad, presentacion, frecuencia, frecuenciaxdia, dosis, hora);

        // 🔹 Guardar en el controlador
        controladorMedicamento.agregarMedicamento(nuevoMedicamento);

        // 🔹 Enviar el medicamento de vuelta a ListaMedicamentosActivity
        Intent resultIntent = new Intent();
        resultIntent.putExtra("nuevoMedicamento", nuevoMedicamento);
        setResult(RESULT_OK, resultIntent);

        // 🔹 Cerrar la actividad y volver a la lista
        finish();
    }


}


