package com.example.grupo2_proyecto.Vista.vistaMedicamento;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo2_proyecto.Modelo.Perfil;
import com.example.grupo2_proyecto.R;
import com.example.grupo2_proyecto.Vista.vistaPerfil.DetallesPerfil;

public class AdministarMedicamentoActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LISTA_MEDICAMENTOS = 1;

    private Button btnlistamedicamento, btnEliminarMedicamento, btnregistrarToma, btnRecargarMedicina, btnrecordatorioRecargar;
    private Perfil perfilActivo; // Guardar el perfil activo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administar_medicamento);

        // 🔹 Obtener el perfil activo desde el Intent
        perfilActivo = (Perfil) getIntent().getSerializableExtra("perfilSeleccionado");

        // 🔹 Verificar si el perfil se pasó correctamente
        if (perfilActivo == null) {
            Toast.makeText(this, "Error: No se encontró el perfil activo.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // 🔹 Inicializar botones
        btnlistamedicamento = findViewById(R.id.listaMedicamento);
        btnEliminarMedicamento = findViewById(R.id.eliminarMedicamento);

        btnRecargarMedicina = findViewById(R.id.btnrecargaMedicina);
        btnrecordatorioRecargar = findViewById(R.id.recordatorioRecargar);

        // 🔹 Configurar eventos de los botones
        configurarBotones();
    }

    // 🔹 Método para manejar eventos de los botones
    private void configurarBotones() {
        btnlistamedicamento.setOnClickListener(v -> abrirActividadParaResultado(ListaMedicamentosActivity.class, REQUEST_CODE_LISTA_MEDICAMENTOS));
        btnEliminarMedicamento.setOnClickListener(v -> abrirActividad(EliminarMedicamentoActivity.class));

        btnRecargarMedicina.setOnClickListener(v -> abrirActividad(RecargaMedicinaActivity.class));
        btnrecordatorioRecargar.setOnClickListener(v -> abrirActividad(RecordatorioMedicina.class));
    }

    // 🔹 Método para abrir una actividad y pasar el perfil activo
    private void abrirActividad(Class<?> actividad) {
        Intent intent = new Intent(this, actividad);
        intent.putExtra("perfilSeleccionado", perfilActivo);
        startActivity(intent);
    }

    // 🔹 Método para abrir una actividad esperando un resultado
    private void abrirActividadParaResultado(Class<?> actividad, int requestCode) {
        Intent intent = new Intent(this, actividad);
        intent.putExtra("perfilSeleccionado", perfilActivo);
        startActivityForResult(intent, requestCode);
    }

    // 🔹 Manejar la respuesta de ListaMedicamentosActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LISTA_MEDICAMENTOS && resultCode == RESULT_OK && data != null) {
            Perfil perfilActualizado = (Perfil) data.getSerializableExtra("perfilSeleccionado");

            if (perfilActualizado != null) {
                perfilActivo = perfilActualizado; // Actualizar el perfil con los nuevos datos
                Toast.makeText(this, "Lista de medicamentos actualizada.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // 🔹 Método para volver al menú principal con el perfil activo
    public void volverAlMenuPrincipal(View view) {
        Intent intent = new Intent(AdministarMedicamentoActivity.this, DetallesPerfil.class);
        intent.putExtra("perfilSeleccionado", perfilActivo);
        startActivity(intent);
        finish(); // Cierra la actividad actual
    }
}


