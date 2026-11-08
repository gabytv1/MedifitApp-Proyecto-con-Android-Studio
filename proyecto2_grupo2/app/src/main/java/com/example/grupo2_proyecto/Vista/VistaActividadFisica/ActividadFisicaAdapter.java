package com.example.grupo2_proyecto.Vista.VistaActividadFisica;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.BaseAdapter;

import com.example.grupo2_proyecto.Modelo.ActividadFisica;
import com.example.grupo2_proyecto.R;

import java.util.List;

public class ActividadFisicaAdapter extends BaseAdapter {
    private Context context;
    private List<ActividadFisica> actividades;

    // Constructor
    public ActividadFisicaAdapter(Context context, List<ActividadFisica> actividades) {
        this.context = context;
        this.actividades = actividades;
    }

    @Override
    public int getCount() {
        return actividades.size();
    }

    @Override
    public Object getItem(int position) {
        return actividades.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    // Método para actualizar la lista de actividades y refrescar el ListView
    public void actualizarLista(List<ActividadFisica> nuevasActividades) {
        this.actividades = nuevasActividades;
        notifyDataSetChanged(); // Refrescar UI
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_actividad_fisica, parent, false);
            holder = new ViewHolder();
            holder.textFecha = convertView.findViewById(R.id.textFecha);
            holder.textActividad = convertView.findViewById(R.id.textActividad);
            holder.textDuracion = convertView.findViewById(R.id.textDuracion);
            holder.textHorario = convertView.findViewById(R.id.textHorario);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Obtener la actividad actual
        ActividadFisica actividad = actividades.get(position);

        // Asignar valores a los TextView
        holder.textFecha.setText("📅 Fecha: " + actividad.getFecha());
        holder.textActividad.setText("🏋️ Actividad: " + actividad.getActividad());
        holder.textDuracion.setText("⏳ Duración: " + actividad.getDuracion() + " min");
        holder.textHorario.setText("⏰ Horario: " + actividad.getHorario());

        return convertView;
    }

    // ViewHolder para mejorar rendimiento
    static class ViewHolder {
        TextView textFecha;
        TextView textActividad;
        TextView textDuracion;
        TextView textHorario;
    }
}

