package com.example.grupo2_proyecto.Vista.VistaActividadFisica;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo2_proyecto.Controlador.ControladorActividadFisica;
import com.example.grupo2_proyecto.Modelo.ActividadFisica;
import com.example.grupo2_proyecto.Modelo.Perfil;
import com.example.grupo2_proyecto.R;
import com.example.grupo2_proyecto.Vista.vistaPerfil.DetallesPerfil;

import java.util.List;

public class ListaActividadFisicaActivity extends AppCompatActivity {

    private static final int REQUEST_NUEVA_ACTIVIDAD = 1;

    private ListView listViewActividades;
    private Button btnRegistrarActividad, btnVolver;
    private ControladorActividadFisica controladorActividadFisica;
    private ActividadFisicaAdapter adapter;
    private List<ActividadFisica> actividades;
    private Perfil perfilActivo; // Guardar el perfil activo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_actividad_fisica);

        listViewActividades = findViewById(R.id.listaActividades);
        btnRegistrarActividad = findViewById(R.id.btnRegistrarActividad);
        btnVolver = findViewById(R.id.btnVolver);

        // Obtener el perfil activo desde el Intent
        perfilActivo = (Perfil) getIntent().getSerializableExtra("perfilSeleccionado");

        if (perfilActivo == null) {
            Toast.makeText(this, "Error: No se encontró el perfil activo.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Inicializar controlador con el perfil activo
        controladorActividadFisica = new ControladorActividadFisica(this, perfilActivo.getNombre());

        // Cargar actividades del perfil activo
        cargarActividades();

        // Botón para ir a la pantalla de registro
        btnRegistrarActividad.setOnClickListener(v -> {
            Intent intent = new Intent(this, RegistrarActividadActivity.class);
            intent.putExtra("perfilSeleccionado", perfilActivo); // Pasar el perfil activo
            startActivityForResult(intent, REQUEST_NUEVA_ACTIVIDAD);
        });

        // Botón para volver a la pantalla de detalles del perfil
        btnVolver.setOnClickListener(v -> {
            Intent intent = new Intent(ListaActividadFisicaActivity.this, DetallesPerfil.class);
            intent.putExtra("perfilSeleccionado", perfilActivo); // Volver con el perfil activo
            startActivity(intent);
            finish(); // Cerrar esta actividad para evitar duplicados en la pila
        });
    }

    /**
     * Método para cargar las actividades del perfil activo y mostrarlas en la lista.
     */
    protected void onResume() {
        super.onResume();
        cargarActividades(); //  Cargar actividades en el momento en que se reabre la pantalla
    }
    private void cargarActividades() {
        actividades = controladorActividadFisica.obtenerListadoActividades();

        if (actividades.isEmpty()) {
            Toast.makeText(this, "No hay actividades registradas.", Toast.LENGTH_SHORT).show();
        } else {
            adapter = new ActividadFisicaAdapter(this, actividades);
            listViewActividades.setAdapter(adapter);
            adapter.notifyDataSetChanged();  //  Asegurar que la lista se actualiza inmediatamente
        }
    }


    /**
     * Manejar el resultado de la actividad de agregar actividad física.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_NUEVA_ACTIVIDAD && resultCode == RESULT_OK && data != null) {
            Perfil perfilActualizado = (Perfil) data.getSerializableExtra("perfilSeleccionado");

            if (perfilActualizado != null) {
                perfilActivo = perfilActualizado; // Actualizar el perfil activo con las nuevas actividades
                cargarActividades(); // Recargar la lista de actividades con los datos actualizados
                Toast.makeText(this, "Actividad registrada correctamente", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
