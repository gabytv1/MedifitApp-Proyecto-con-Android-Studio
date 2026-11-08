package com.example.grupo2_proyecto.Vista.VistaMedico;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.grupo2_proyecto.R;
import java.util.List;

public class MedicoAdapter extends RecyclerView.Adapter<MedicoAdapter.MedicoViewHolder> {

    private List<String> medicos;

    public MedicoAdapter(List<String> medicos) {
        this.medicos = medicos;
    }

    @Override
    public MedicoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medico, parent, false);
        return new MedicoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicoViewHolder holder, int position) {
        String medico = medicos.get(position);
        holder.textViewNombre.setText(medico);
    }

    @Override
    public int getItemCount() {
        return medicos.size();
    }

    public static class MedicoViewHolder extends RecyclerView.ViewHolder {
        TextView textViewNombre;

        public MedicoViewHolder(View itemView) {
            super(itemView);
            textViewNombre = itemView.findViewById(R.id.textViewNombre);
        }
    }
}
