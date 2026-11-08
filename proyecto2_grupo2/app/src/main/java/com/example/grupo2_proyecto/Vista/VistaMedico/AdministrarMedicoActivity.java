package com.example.grupo2_proyecto.Vista.VistaMedico;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.grupo2_proyecto.Controlador.ControladorMedico;
import com.example.grupo2_proyecto.Modelo.Perfil;
import com.example.grupo2_proyecto.R;
import com.example.grupo2_proyecto.Vista.vistaPerfil.DetallesPerfil;

import java.util.List;

public class AdministrarMedicoActivity extends AppCompatActivity {

    private RecyclerView recyclerViewMedicos;
    private Button buttonAgregarMedico;
    private ControladorMedico controladorMedico;
    private Perfil perfilActivo; // Guardar el perfil seleccionado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrar_medico);

        recyclerViewMedicos = findViewById(R.id.recyclerViewMedicos);
        buttonAgregarMedico = findViewById(R.id.buttonAgregarMedico);

        recyclerViewMedicos.setLayoutManager(new LinearLayoutManager(this));

        // Obtener el perfil activo desde el Intent
        perfilActivo = (Perfil) getIntent().getSerializableExtra("perfilSeleccionado");

        if (perfilActivo == null) {
            // Si no se encuentra el perfil, muestra un mensaje de error y cierra la actividad
            finish();
            return;
        }

        // Inicializar el controlador con el nombre del perfil activo
        controladorMedico = new ControladorMedico(this, perfilActivo.getNombre());

        // Actualizar la lista de médicos
        actualizarListaMedicos();

        // Configurar el botón para agregar médico
        buttonAgregarMedico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdministrarMedicoActivity.this, AgregarMedicoActivity.class);
                intent.putExtra("perfilSeleccionado", perfilActivo); // Enviar el perfil actual
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista de médicos al regresar a la actividad
        actualizarListaMedicos();
    }

    private void actualizarListaMedicos() {
        List<String> listaMedicos = controladorMedico.obtenerMedicos(); // Obtener los médicos en formato String
        MedicoAdapter medicoAdapter = new MedicoAdapter(listaMedicos);
        recyclerViewMedicos.setAdapter(medicoAdapter); // Configurar el adaptador
    }

    public void volverAlMenuPrincipal(View view) {
        Intent intent = new Intent(AdministrarMedicoActivity.this, DetallesPerfil.class);
        intent.putExtra("perfilSeleccionado", perfilActivo); // Pasar el perfil de vuelta
        startActivity(intent);
        finish(); // Cierra la actividad actual para evitar acumulación de pantallas
    }
}

