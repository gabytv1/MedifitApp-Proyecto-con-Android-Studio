package com.example.grupo2_proyecto.Vista.VistaMedico;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo2_proyecto.Controlador.ControladorMedico;
import com.example.grupo2_proyecto.Controlador.ControladorPerfil;
import com.example.grupo2_proyecto.Modelo.Medico;
import com.example.grupo2_proyecto.Modelo.Perfil;
import com.example.grupo2_proyecto.R;

public class AgregarMedicoActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextEspecialidad, editTextTelefono, editTextEmail, editTextDireccion;
    private Button buttonGuardar;
    private ControladorMedico controladorMedico;
    private ControladorPerfil controladorPerfil; // Controlador para actualizar el perfil
    private Perfil perfilActivo; // Guardar el perfil actual

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_medico);

        // Inicializar vistas
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextEspecialidad = findViewById(R.id.editTextEspecialidad);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDireccion = findViewById(R.id.editTextDireccion);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        // Obtener el perfil activo desde el Intent
        perfilActivo = (Perfil) getIntent().getSerializableExtra("perfilSeleccionado");

        if (perfilActivo == null) {
            Toast.makeText(this, "Error: No se encontró el perfil activo.", Toast.LENGTH_SHORT).show();
            finish(); // Cerrar actividad si no hay perfil
            return;
        }

        // Inicializar los controladores
        controladorMedico = new ControladorMedico(this, perfilActivo.getNombre());
        controladorPerfil = new ControladorPerfil(this);

        // Configurar el listener para el botón Guardar
        buttonGuardar.setOnClickListener(v -> guardarDatos());
    }

    // Método para guardar los datos
    private void guardarDatos() {
        String nombre = editTextNombre.getText().toString().trim();
        String especialidad = editTextEspecialidad.getText().toString().trim();
        String telefono = editTextTelefono.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String direccion = editTextDireccion.getText().toString().trim();

        // Validar que no haya campos vacíos
        if (nombre.isEmpty() || especialidad.isEmpty() || telefono.isEmpty() || email.isEmpty() || direccion.isEmpty()) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear el objeto Medico
        Medico nuevoMedico = new Medico(nombre, especialidad, telefono, email, direccion);

        // Agregar el médico en el controlador
        controladorMedico.agregarMedico(nombre, especialidad, telefono, email, direccion);

        // Agregar el médico al perfil activo
        perfilActivo.agregarMedico(nuevoMedico);

        // Guardar cambios en el perfil
        controladorPerfil.actualizarPerfil(perfilActivo);

        // Mostrar mensaje de éxito
        Toast.makeText(this, "Médico guardado correctamente para " + perfilActivo.getNombre(), Toast.LENGTH_SHORT).show();

        // Limpiar los campos después de guardar
        limpiarCampos();

        // Regresar a la actividad de administración con el perfil activo
        Intent intent = new Intent(AgregarMedicoActivity.this, AdministrarMedicoActivity.class);
        intent.putExtra("perfilSeleccionado", perfilActivo);
        startActivity(intent);
        finish();
    }

    private void limpiarCampos() {
        editTextNombre.setText("");
        editTextEspecialidad.setText("");
        editTextTelefono.setText("");
        editTextEmail.setText("");
        editTextDireccion.setText("");
    }
}

