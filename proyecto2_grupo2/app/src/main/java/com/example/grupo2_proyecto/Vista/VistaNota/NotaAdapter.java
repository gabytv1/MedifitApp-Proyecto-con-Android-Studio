package com.example.grupo2_proyecto.Vista.VistaNota;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.example.grupo2_proyecto.Modelo.Nota;
import com.example.grupo2_proyecto.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NotaAdapter extends ArrayAdapter<Nota> {

    public NotaAdapter(Context context, List<Nota> notas) {
        super(context, 0, notas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_nota, parent, false);
        }

        // Obtener la nota actual
        Nota nota = getItem(position);

        // Obtener referencias a los TextViews
        TextView tvNotaTexto = convertView.findViewById(R.id.tvNotaTexto);
        TextView tvFechaNota = convertView.findViewById(R.id.tvFechaNota);
        TextView tvHoraNota = convertView.findViewById(R.id.tvHoraNota);

        // Establecer valores en los TextViews si la nota no es nula
        if (nota != null) {
            tvNotaTexto.setText(nota.getDescripcion()); // Método corregido para obtener el texto de la nota

            if (nota.getFechaHora() != null) {
                // Formatear la fecha y la hora correctamente
                SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm", Locale.getDefault());

                tvFechaNota.setText("Fecha: " + formatoFecha.format(nota.getFechaHora()));
                tvHoraNota.setText("Hora: " + formatoHora.format(nota.getFechaHora()));
            } else {
                tvFechaNota.setText("Fecha: No disponible");
                tvHoraNota.setText("Hora: No disponible");
            }
        }

        return convertView;
    }
}


