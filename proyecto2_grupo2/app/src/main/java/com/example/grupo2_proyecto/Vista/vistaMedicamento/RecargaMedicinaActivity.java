package com.example.grupo2_proyecto.Vista.vistaMedicamento;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.grupo2_proyecto.Controlador.ControladorMedicamento;
import com.example.grupo2_proyecto.Modelo.Perfil;
import com.example.grupo2_proyecto.R;

public class RecargaMedicinaActivity extends AppCompatActivity {

    private Button btnAgregarMedicina;
    private EditText editTextNombreMedicina;
    private EditText editTextCantidad;
    private ControladorMedicamento controladorMedicamento;
    private Perfil perfilActivo; // Guardar el perfil activo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recarga_medicina);

        editTextNombreMedicina = findViewById(R.id.editTextNombreMedicina);
        editTextCantidad = findViewById(R.id.editTextCantidad);
        btnAgregarMedicina = findViewById(R.id.btnAgregarMedicina);

        // Obtener el perfil activo desde el Intent
        perfilActivo = (Perfil) getIntent().getSerializableExtra("perfilSeleccionado");

        if (perfilActivo == null) {
            Toast.makeText(this, "Error: No se encontró el perfil activo.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Inicializar controlador con el perfil activo
        controladorMedicamento = new ControladorMedicamento(this, perfilActivo.getNombre());

        btnAgregarMedicina.setOnClickListener(v -> recargarMedicamento());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void recargarMedicamento() {
        String nombreMedicamento = editTextNombreMedicina.getText().toString().trim();
        String cantidadMedicamentoStr = editTextCantidad.getText().toString().trim();

        if (nombreMedicamento.isEmpty() || cantidadMedicamentoStr.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return;
        }

        int cantidadMedicamento;
        try {
            cantidadMedicamento = Integer.parseInt(cantidadMedicamentoStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Ingrese una cantidad válida.", Toast.LENGTH_SHORT).show();
            return;
        }

        String resultado = controladorMedicamento.recargarMedicamento(nombreMedicamento, cantidadMedicamento);
        Toast.makeText(this, resultado, Toast.LENGTH_SHORT).show();

        Intent returnIntent = new Intent();
        returnIntent.putExtra("perfilSeleccionado", perfilActivo);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
