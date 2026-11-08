package com.example.grupo2_proyecto.Vista.VistaCitaMedica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.grupo2_proyecto.Modelo.CitaMedica;
import com.example.grupo2_proyecto.R;

import java.util.List;

public class CitaMedicaAdapterActivity extends ArrayAdapter<CitaMedica> {

    public CitaMedicaAdapterActivity(Context context, List<CitaMedica> citas) {
        super(context, 0, citas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cita_medica, parent, false);
            holder = new ViewHolder();
            holder.tvTitulo = convertView.findViewById(R.id.tvTituloCita);
            holder.tvDoctor = convertView.findViewById(R.id.tvDoctor);
            holder.tvFechaHora = convertView.findViewById(R.id.tvFechaHora);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Obtener la cita actual
        CitaMedica cita = getItem(position);

        if (cita != null) {
            holder.tvTitulo.setText(cita.getTitulo());
            holder.tvDoctor.setText("Médico: " + (cita.getMedicoNombre() != null ? cita.getMedicoNombre() : "No asignado"));
            holder.tvFechaHora.setText("Fecha y Hora: " + cita.getFechaHora());
        }

        return convertView;
    }

    static class ViewHolder {
        TextView tvTitulo;
        TextView tvDoctor;
        TextView tvFechaHora;
    }
}
