package com.example.grupo2_proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.grupo2_proyecto.Vista.vistaPerfil.CrearPerfilActivity;
import com.example.grupo2_proyecto.Vista.vistaPerfil.SeleccionarPerfilActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 🔹 Inicializar botones
        Button buttonCrearPerfil = findViewById(R.id.button_crear_perfil);
        Button buttonSeleccionarPerfil = findViewById(R.id.button_seleccionar_perfil);

        // 🔹 Navegar a Crear Perfil
        buttonCrearPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CrearPerfilActivity.class);
            startActivity(intent);
        });

        // 🔹 Navegar a Seleccionar Perfil
        buttonSeleccionarPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SeleccionarPerfilActivity.class);
            startActivity(intent);
        });
    }
}


