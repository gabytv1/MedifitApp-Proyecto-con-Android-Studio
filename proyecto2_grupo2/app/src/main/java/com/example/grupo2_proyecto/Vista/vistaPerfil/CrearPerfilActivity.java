package com.example.grupo2_proyecto.Vista.vistaPerfil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo2_proyecto.Controlador.ControladorPerfil;
import com.example.grupo2_proyecto.MainActivity;
import com.example.grupo2_proyecto.Modelo.Perfil;
import com.example.grupo2_proyecto.R;

public class CrearPerfilActivity extends AppCompatActivity {
    private EditText editTextNombre, editTextApellido, editTextEmail;
    private Button buttonGuardarPerfil;
    private ControladorPerfil controladorPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_perfil);

        // Inicializar las vistas
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellido = findViewById(R.id.editTextApellido);
        editTextEmail = findViewById(R.id.editTextEmail);
        buttonGuardarPerfil = findViewById(R.id.button_guardar_perfil);

        // Crear el controlador, pasando 'this' como contexto
        controladorPerfil = new ControladorPerfil(this);

        // Configurar el botón para guardar el perfil
        buttonGuardarPerfil.setOnClickListener(v -> {
            String nombre = editTextNombre.getText().toString();
            String apellido = editTextApellido.getText().toString();
            String email = editTextEmail.getText().toString();

            if (!nombre.isEmpty() && !apellido.isEmpty() && !email.isEmpty()) {
                Perfil perfil = new Perfil(nombre, apellido, email);
                controladorPerfil.agregarPerfil(perfil);

                Toast.makeText(this, "Perfil guardado exitosamente", Toast.LENGTH_SHORT).show();
                finish();  // Regresa a la actividad principal
            } else {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void volverAlMenuPrincipal(View view) {
        Intent intent = new Intent(CrearPerfilActivity.this, MainActivity.class);
        startActivity(intent);
    }
}




