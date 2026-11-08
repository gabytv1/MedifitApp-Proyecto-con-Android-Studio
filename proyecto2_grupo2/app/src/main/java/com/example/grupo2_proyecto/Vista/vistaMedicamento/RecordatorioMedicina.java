package com.example.grupo2_proyecto.Vista.vistaMedicamento;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo2_proyecto.Controlador.ControladorMedicamento;
import com.example.grupo2_proyecto.Modelo.Perfil;
import com.example.grupo2_proyecto.R;

public class RecordatorioMedicina extends AppCompatActivity {

    private EditText editTextNombreMedicamento;
    private EditText editTextCantidadUmbral;
    private Button btnEstablecerRecordatorio;
    private Perfil perfilActivo;
    private ControladorMedicamento controladorMedicamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recordatorio_medicina);

        // 🔹 Obtener el perfil activo desde el Intent
        perfilActivo = (Perfil) getIntent().getSerializableExtra("perfilSeleccionado");

        if (perfilActivo == null) {
            Toast.makeText(this, "Error: No se encontró el perfil activo.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 🔹 Inicializar el controlador con el perfil activo
        controladorMedicamento = new ControladorMedicamento(this, perfilActivo.getNombre());

        // 🔹 Inicializar vistas
        editTextNombreMedicamento = findViewById(R.id.editTextNombreMedicamento);
        editTextCantidadUmbral = findViewById(R.id.editTextCantidadUmbral);
        btnEstablecerRecordatorio = findViewById(R.id.btnEstablecerRecordatorio);

        // 🔹 Configurar el botón de establecer recordatorio
        btnEstablecerRecordatorio.setOnClickListener(v -> establecerRecordatorio());
    }

    private void establecerRecordatorio() {
        String nombreMedicamento = editTextNombreMedicamento.getText().toString().trim();
        String cantidadUmbralStr = editTextCantidadUmbral.getText().toString().trim();

        // 🔹 Validar entradas
        if (nombreMedicamento.isEmpty() || cantidadUmbralStr.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        int cantidadUmbral;
        try {
            cantidadUmbral = Integer.parseInt(cantidadUmbralStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingrese un número válido.", Toast.LENGTH_SHORT).show();
            return;
        }

        // 🔹 Llamar al método correcto del controlador
        String resultado = controladorMedicamento.establecerUmbral(nombreMedicamento, cantidadUmbral);
        Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();

        // 🔹 Devolver el perfil actualizado a la actividad anterior
        Intent resultIntent = new Intent();
        resultIntent.putExtra("perfilSeleccionado", perfilActivo);
        setResult(RESULT_OK, resultIntent);

        finish(); // Cerrar la actividad
    }
}
