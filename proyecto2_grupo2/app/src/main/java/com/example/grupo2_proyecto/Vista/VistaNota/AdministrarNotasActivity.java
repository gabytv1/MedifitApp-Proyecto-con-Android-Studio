package com.example.grupo2_proyecto.Vista.VistaNota;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo2_proyecto.Controlador.ControladorNota;
import com.example.grupo2_proyecto.Modelo.Nota;
import com.example.grupo2_proyecto.Modelo.Perfil;
import com.example.grupo2_proyecto.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AdministrarNotasActivity extends AppCompatActivity {

    private ListView listaNotas;
    private NotaAdapter adapter;
    private ControladorNota controladorNota;
    private Perfil perfilActivo;
    private List<Nota> notas;

    // Lanzador de actividad para recibir resultados de FormularioNotaActivity
    private final ActivityResultLauncher<Intent> agregarNotaLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    String nuevaNotaDescripcion = result.getData().getStringExtra("nuevaNota");

                    if (nuevaNotaDescripcion != null) {
                        agregarNotaEnTiempoReal(nuevaNotaDescripcion);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_notas);

        // Obtener el perfil activo
        perfilActivo = (Perfil) getIntent().getSerializableExtra("perfilSeleccionado");

        if (perfilActivo == null) {
            Toast.makeText(this, "Error: No se encontró el perfil activo.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Inicializar el controlador de notas con el perfil
        controladorNota = new ControladorNota(this, perfilActivo.getNombre());

        // Inicializar la lista de notas
        listaNotas = findViewById(R.id.listViewNotas);
        notas = controladorNota.obtenerNotas(); // Obtener notas del perfil
        ordenarNotas(); // Ordenar por fecha
        adapter = new NotaAdapter(this, notas);
        listaNotas.setAdapter(adapter);

        // Botón para agregar una nueva nota
        Button btnAgregarNota = findViewById(R.id.btnAgregarNota);
        btnAgregarNota.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormularioNotaActivity.class);
            intent.putExtra("perfilSeleccionado", perfilActivo);
            agregarNotaLauncher.launch(intent); // 🔹 Esperar resultado correctamente
        });

        // Botón para regresar
        Button btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(v -> finish());
    }

    /**
     * Ordena las notas de más reciente a más antigua por fecha y hora.
     */
    private void ordenarNotas() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notas.sort(Comparator.comparing(Nota::getFechaHora).reversed());
        }
    }

    /**
     * Agrega una nueva nota en tiempo real sin recargar la actividad.
     */
    private void agregarNotaEnTiempoReal(String descripcion) {
        Nota nuevaNota = new Nota(descripcion);
        notas.add(0, nuevaNota); // Agregar en la primera posición para que sea la más reciente
        controladorNota.agregarNota(descripcion); // Guardar en el controlador
        ordenarNotas(); // Ordenar de más reciente a más antigua
        adapter.notifyDataSetChanged(); // Refrescar la lista en tiempo real
        Toast.makeText(this, "Nota agregada correctamente", Toast.LENGTH_SHORT).show();
    }
}



