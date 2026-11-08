package com.example.grupo2_proyecto.Vista.vistaMedicamento;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo2_proyecto.Controlador.ControladorMedicamento;
import com.example.grupo2_proyecto.Modelo.Perfil;
import com.example.grupo2_proyecto.R;

public class EliminarMedicamentoActivity extends AppCompatActivity {
    private EditText etNombreEliminar;
    private Button btnEliminarMedicamento2;
    private Perfil perfilActivo; // Guardar el perfil activo
    private ControladorMedicamento controladorMedicamento; // Controlador para el perfil

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_medicamento);

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
        etNombreEliminar = findViewById(R.id.etNombreEliminar);
        btnEliminarMedicamento2 = findViewById(R.id.eliminarMedicamento2);

        // 🔹 Configurar botón de eliminación
        btnEliminarMedicamento2.setOnClickListener(v -> eliminarMedicamento());
    }

    // 🔹 Método para eliminar un medicamento
    private void eliminarMedicamento() {
        String nombre = etNombreEliminar.getText().toString().trim();

        if (nombre.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese un nombre", Toast.LENGTH_SHORT).show();
            return;
        }

        // 🔹 Eliminar del perfil y guardar cambios
        boolean eliminado = controladorMedicamento.eliminarMedicamento(nombre);

        if (eliminado) {
            Toast.makeText(this, "Medicamento eliminado correctamente", Toast.LENGTH_SHORT).show();
            finish(); // Cerrar la actividad después de eliminar
        } else {
            Toast.makeText(this, "Medicamento no encontrado", Toast.LENGTH_SHORT).show();
        }
    }
}

