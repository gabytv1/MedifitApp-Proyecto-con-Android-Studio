package com.example.grupo2_proyecto.Vista.VistaCitaMedica;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo2_proyecto.Controlador.ControladorCitaMedica;
import com.example.grupo2_proyecto.Modelo.CitaMedica;
import com.example.grupo2_proyecto.Modelo.Perfil;
import com.example.grupo2_proyecto.R;
import com.example.grupo2_proyecto.Vista.vistaPerfil.DetallesPerfil;

import java.util.List;

public class AdministrarCitaActivity extends AppCompatActivity {

    private static final int REQUEST_NUEVA_CITA = 1;

    private ListView listaCitas;
    private Button btnAgregarCita;
    private ControladorCitaMedica controladorCitaMedica;
    private CitaMedicaAdapterActivity adapter;
    private List<CitaMedica> citas;
    private Perfil perfilActivo; // Guardar el perfil activo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_cita);

        // Inicializar vistas
        listaCitas = findViewById(R.id.listaCitas);
        btnAgregarCita = findViewById(R.id.btnAgregarCita);

        // Obtener el perfil activo desde el Intent
        perfilActivo = (Perfil) getIntent().getSerializableExtra("perfilSeleccionado");

        if (perfilActivo == null) {
            Toast.makeText(this, "Error: No se encontró el perfil activo.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Inicializar controlador con el perfil activo
        controladorCitaMedica = new ControladorCitaMedica(this, perfilActivo.getNombre());

        // Cargar citas del perfil activo
        cargarCitas();

        // Configurar el botón para agregar una nueva cita médica
        btnAgregarCita.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormularioCitaActivity.class);
            intent.putExtra("perfilSeleccionado", perfilActivo); // Pasar el perfil activo
            startActivityForResult(intent, REQUEST_NUEVA_CITA);
        });
    }

    /**
     * Método para cargar las citas desde el controlador y mostrarlas en la lista.
     */
    private void cargarCitas() {
        citas = controladorCitaMedica.obtenerCitas();

        if (adapter == null) {
            adapter = new CitaMedicaAdapterActivity(this, citas);
            listaCitas.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged(); // 🔹 Actualiza en tiempo real
        }
    }

    /**
     * 🔹 Método para recibir la cita y actualizar la lista inmediatamente.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            CitaMedica nuevaCita = (CitaMedica) data.getSerializableExtra("nuevaCita");

            if (nuevaCita != null) {
                citas.add(nuevaCita); // 🔹 Agregar la cita a la lista en memoria
                adapter.notifyDataSetChanged(); // 🔹 Refrescar el ListView en tiempo real
                Toast.makeText(this, "Cita agregada correctamente", Toast.LENGTH_SHORT).show();
            }
        }
    }



    /**
     * Método para volver a la pantalla de DetallesPerfil sin perder los datos del perfil activo.
     */
    public void volverAlMenuPrincipal(View view) {
        Intent intent = new Intent(AdministrarCitaActivity.this, DetallesPerfil.class);
        intent.putExtra("perfilSeleccionado", perfilActivo);
        startActivity(intent);
        finish();
    }
}





