package com.example.grupo2_proyecto.Vista.vistaMedicamento;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.grupo2_proyecto.Controlador.ControladorMedicamento;
import com.example.grupo2_proyecto.Modelo.Medicamento;
import com.example.grupo2_proyecto.Modelo.Perfil;
import com.example.grupo2_proyecto.R;
import java.util.List;

public class ListaMedicamentosActivity extends AppCompatActivity {

    private ListView listaMedicamentos;
    private ControladorMedicamento controladorMedicamento;
    private MedicamentoAdapter adapter;
    private List<Medicamento> listaMedicamentos2;
    private Perfil perfilActivo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_medicamentos);

        listaMedicamentos = findViewById(R.id.listaMedicamento);

        // Obtener el perfil activo desde el Intent
        perfilActivo = (Perfil) getIntent().getSerializableExtra("perfilSeleccionado");

        if (perfilActivo == null) {
            Toast.makeText(this, "Error: No se encontró el perfil activo", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Inicializar el controlador con el perfil activo
        controladorMedicamento = new ControladorMedicamento(this, perfilActivo.getNombre());

        // Cargar la lista de medicamentos
        cargarMedicamentos();

        // Configurar clic en un medicamento
        listaMedicamentos.setOnItemClickListener((parent, view, position, id) -> {
            Medicamento medicamentoSeleccionado = listaMedicamentos2.get(position);
            mostrarDialogoAccion(medicamentoSeleccionado);
        });

        // Botón para agregar un nuevo medicamento
        Button btnAgregarMedicamento = findViewById(R.id.agregarMedicamento);
        btnAgregarMedicamento.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormularioMedicamento.class);
            intent.putExtra("perfilSeleccionado", perfilActivo);
            startActivityForResult(intent, 1);
        });
    }

    private void cargarMedicamentos() {
        listaMedicamentos2 = controladorMedicamento.obtenerMedicamentos();

        if (adapter == null) {
            adapter = new MedicamentoAdapter(listaMedicamentos2);
            listaMedicamentos.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    /**
     * 🔹 Muestra un diálogo con opciones al tocar un medicamento.
     */
    private void mostrarDialogoAccion(Medicamento medicamento) {
        DialogoRegistrarToma dialogo = new DialogoRegistrarToma(medicamento, controladorMedicamento, () -> {
            cargarMedicamentos();  // Recargar la lista tras cambios
            adapter.notifyDataSetChanged(); // 🔹 Asegurar actualización visual
        });
        dialogo.show(getSupportFragmentManager(), "DialogoRegistrarToma");
    }



    // Adaptador para mostrar medicamentos en el ListView
    private class MedicamentoAdapter extends BaseAdapter {
        private List<Medicamento> medicamentos;

        public MedicamentoAdapter(List<Medicamento> medicamentos) {
            this.medicamentos = medicamentos;
        }

        @Override
        public int getCount() {
            return medicamentos.size();
        }

        @Override
        public Object getItem(int position) {
            return medicamentos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_medicamento, parent, false);
            }

            // Obtener la instancia del medicamento actual
            Medicamento medicamento = medicamentos.get(position);

            // Configurar los textos
            TextView textNombre = convertView.findViewById(R.id.textNombre);
            TextView textDetalles = convertView.findViewById(R.id.textDetalles);
            TextView textUltimaToma = convertView.findViewById(R.id.textUltimaToma);

            textNombre.setText(medicamento.getNombre());
            textDetalles.setText(
                    "Cantidad: " + medicamento.getCantidad() + "\n" +
                            "Presentación: " + medicamento.getPresentacion() + "\n" +
                            "Frecuencia: " + medicamento.getFrecuencia() + " días por semana" + "\n" +
                            "Frecuencia diaria: " + medicamento.getFrecuenciaXdia() + " veces al día" + "\n" +
                            "Dosis: " + medicamento.getDosis() + "\n" +
                            "Hora: " + medicamento.getHora()
            );

            if (medicamento.getUltimaToma() != null) {
                textUltimaToma.setText("Última toma: " + medicamento.getUltimaToma());
            } else {
                textUltimaToma.setText("No se ha registrado una toma aún");
            }

            return convertView;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Medicamento nuevoMedicamento = (Medicamento) data.getSerializableExtra("nuevoMedicamento");

            if (nuevoMedicamento != null) {
                controladorMedicamento.agregarMedicamento(nuevoMedicamento); // 🔹 Guardar en controlador
                listaMedicamentos2.add(nuevoMedicamento); // 🔹 Agregar a la lista local
                adapter.notifyDataSetChanged(); // 🔹 Refrescar ListView en tiempo real
            }
        }
    }

}




