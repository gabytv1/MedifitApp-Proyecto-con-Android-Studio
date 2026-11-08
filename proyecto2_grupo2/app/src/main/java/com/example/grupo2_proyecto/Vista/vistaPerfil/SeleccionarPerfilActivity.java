// Código de SeleccionarPerfilActivity.java
package com.example.grupo2_proyecto.Vista.vistaPerfil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo2_proyecto.Controlador.ControladorPerfil;
import com.example.grupo2_proyecto.MainActivity;
import com.example.grupo2_proyecto.Modelo.Perfil;
import com.example.grupo2_proyecto.R;

import java.util.ArrayList;

public class SeleccionarPerfilActivity extends AppCompatActivity {
    private ListView listViewPerfiles;
    private ControladorPerfil controladorPerfil;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccionar_perfil);

        listViewPerfiles = findViewById(R.id.listViewPerfiles);
        controladorPerfil = new ControladorPerfil(this);

        // Cargar perfiles inicialmente
        cargarPerfiles();

        // Configurar el clic en un perfil de la lista
        listViewPerfiles.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Perfil perfilSeleccionado = controladorPerfil.obtenerPerfiles().get(position);

                // Enviar el perfil seleccionado a OtraActividad
                Intent intent = new Intent(SeleccionarPerfilActivity.this, DetallesPerfil.class);
                intent.putExtra("perfilSeleccionado", perfilSeleccionado);
                startActivity(intent);
            }
        });
    }

    private void cargarPerfiles() {
        ArrayList<Perfil> perfiles = controladorPerfil.obtenerPerfiles();
        ArrayList<String> nombresPerfiles = new ArrayList<>();

        if (perfiles != null && !perfiles.isEmpty()) {
            for (Perfil perfil : perfiles) {
                nombresPerfiles.add(perfil.getNombre());
            }

            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, nombresPerfiles);
            listViewPerfiles.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No se han creado perfiles", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarPerfiles();  // Recargar la lista de perfiles después de eliminar uno
        actualizarLista();  // Si estás usando un adaptador, actualízalo también
    }

    private void actualizarLista() {
        ArrayList<Perfil> perfiles = controladorPerfil.obtenerPerfiles();
        ArrayList<String> nombresPerfiles = new ArrayList<>();
        for (Perfil perfil : perfiles) {
            nombresPerfiles.add(perfil.getNombre());
        }

        // Actualiza el adaptador
        adapter.clear();
        adapter.addAll(nombresPerfiles);
        adapter.notifyDataSetChanged();
    }

    // Método para regresar al menú principal
    public void volverAlMenuPrincipal(View view) {
        Intent intent = new Intent(SeleccionarPerfilActivity.this, MainActivity.class);
        startActivity(intent);
        finish();  // Finaliza la actividad actual para que no se quede en la pila
    }
    private void guardarPerfilSeleccionado(Perfil perfilSeleccionado) {
        SharedPreferences sharedPreferences = getSharedPreferences("PerfilSeleccionadoPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
    }
}



