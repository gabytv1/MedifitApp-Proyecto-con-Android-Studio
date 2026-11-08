package com.example.grupo2_proyecto.Vista.vistaPerfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo2_proyecto.Controlador.ControladorPerfil;
import com.example.grupo2_proyecto.Modelo.Perfil;
import com.example.grupo2_proyecto.R;
import com.example.grupo2_proyecto.Vista.VistaCitaMedica.AdministrarCitaActivity;
import com.example.grupo2_proyecto.Vista.VistaMedico.AdministrarMedicoActivity;
import com.example.grupo2_proyecto.Vista.VistaActividadFisica.ListaActividadFisicaActivity;
import com.example.grupo2_proyecto.Vista.VistaNota.AdministrarNotasActivity;
import com.example.grupo2_proyecto.Vista.vistaMedicamento.AdministarMedicamentoActivity;

public class DetallesPerfil extends AppCompatActivity {

    private TextView textViewPerfilInfo;
    private ControladorPerfil controladorPerfil;
    private Perfil perfilSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_perfil);

        textViewPerfilInfo = findViewById(R.id.textViewPerfilInfo);
        Button buttonEliminarPerfil = findViewById(R.id.buttonEliminarPerfil);
        Button buttonAdministrarMedicamento = findViewById(R.id.admiMedicamento);
        Button buttonAdministrarCitas = findViewById(R.id.administrarCitas);
        Button buttonAdministrarMedico = findViewById(R.id.administrarMedico);
        Button buttonAdministrarActividadFisica = findViewById(R.id.administrarActFisica);
        Button buttonAdministrarNotas= findViewById(R.id.administrarSalud);

        Intent intent = getIntent();
        perfilSeleccionado = (Perfil) intent.getSerializableExtra("perfilSeleccionado");

        if (perfilSeleccionado != null) {
            textViewPerfilInfo.setText("Nombre: " + perfilSeleccionado.getNombre() + "\n" +
                    "Apellido: " + perfilSeleccionado.getApellido() + "\n" +
                    "Correo: " + perfilSeleccionado.getEmail());
        } else {
            Toast.makeText(this, "Error al cargar el perfil", Toast.LENGTH_SHORT).show();
            finish(); // Cierra la actividad si no hay perfil seleccionado
            return;
        }

        // Inicializar controlador
        controladorPerfil = new ControladorPerfil(this);

        // Configurar botón de eliminar perfil
        buttonEliminarPerfil.setOnClickListener(v -> {
            controladorPerfil.eliminarPerfil(perfilSeleccionado);
            Toast.makeText(DetallesPerfil.this, "Perfil eliminado", Toast.LENGTH_SHORT).show();

            // Regresar a la actividad de selección de perfil y cerrar esta actividad
            Intent intentRegresar = new Intent(DetallesPerfil.this, SeleccionarPerfilActivity.class);
            startActivity(intentRegresar);
            finish();
        });

        // Configurar botón para administrar medicamentos
        buttonAdministrarMedicamento.setOnClickListener(v -> {
            Intent intentMedicamento = new Intent(DetallesPerfil.this, AdministarMedicamentoActivity.class);
            intentMedicamento.putExtra("perfilSeleccionado", perfilSeleccionado);
            startActivity(intentMedicamento);
        });

        // Configurar botón para administrar citas médicas
        buttonAdministrarCitas.setOnClickListener(v -> {
            Intent intentCitas = new Intent(DetallesPerfil.this, AdministrarCitaActivity.class);
            intentCitas.putExtra("perfilSeleccionado", perfilSeleccionado);
            startActivity(intentCitas);
        });

        // Configurar botón para administrar médicos
        buttonAdministrarMedico.setOnClickListener(v -> {
            Intent intentMedico = new Intent(DetallesPerfil.this, AdministrarMedicoActivity.class);
            intentMedico.putExtra("perfilSeleccionado", perfilSeleccionado);
            startActivity(intentMedico);
        });

        // Configurar botón para administrar actividad física
        buttonAdministrarActividadFisica.setOnClickListener(v -> {
            Intent intentFisica = new Intent(DetallesPerfil.this, ListaActividadFisicaActivity.class);
            intentFisica.putExtra("perfilSeleccionado", perfilSeleccionado);
            startActivity(intentFisica);
        });
        //Configurar boton para notas
        buttonAdministrarNotas.setOnClickListener(v -> {
            Intent intentno= new Intent(DetallesPerfil.this, AdministrarNotasActivity.class);
            intentno.putExtra("perfilSeleccionado", perfilSeleccionado);
            startActivity(intentno);
        });

    }

    // Método para regresar al menú principal
    public void volverAlMenuPrincipal(View view) {
        Intent intent = new Intent(DetallesPerfil.this, SeleccionarPerfilActivity.class);
        intent.putExtra("perfilSeleccionado", perfilSeleccionado);
        startActivity(intent);
        finish(); // Cierra esta actividad
    }
}

