package com.example.grupo2_proyecto.Vista.vistaMedicamento;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;
import com.example.grupo2_proyecto.Controlador.ControladorMedicamento;
import com.example.grupo2_proyecto.Modelo.Medicamento;
import com.example.grupo2_proyecto.R;
import java.util.Calendar;

public class DialogoRegistrarToma extends DialogFragment {

    private Medicamento medicamento;
    private ControladorMedicamento controladorMedicamento;
    private OnDialogResultListener listener;

    public interface OnDialogResultListener {
        void onTomaRegistrada();
    }

    public DialogoRegistrarToma(Medicamento medicamento, ControladorMedicamento controlador, OnDialogResultListener listener) {
        this.medicamento = medicamento;
        this.controladorMedicamento = controlador;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        // Inflar el layout personalizado (corrigiendo el nombre del archivo XML)
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.diaologo_registrar_toma, null);
        builder.setView(view);

        // Inicializar elementos del layout
        TextView tvNombreMedicamento = view.findViewById(R.id.tvNombreMedicamento);
        TextView tvEstadoToma = view.findViewById(R.id.tvEstadoToma);
        Button btnTomar = view.findViewById(R.id.btnTomar);
        Button btnPasar = view.findViewById(R.id.btnPasar);
        Button btnReprogramar = view.findViewById(R.id.btnReprogramar);

        // Establecer el nombre y estado del medicamento
        tvNombreMedicamento.setText(medicamento.getNombre());
        tvEstadoToma.setText("⏳ Agendado para: " + medicamento.getHora() + "\n🕐 Última toma: " + medicamento.getUltimaToma());

        // Acciones de botones
        btnTomar.setOnClickListener(v -> {
            registrarToma();
            dismiss();
        });

        btnPasar.setOnClickListener(v -> {
            omitirToma();
            dismiss();
        });

        btnReprogramar.setOnClickListener(v -> {
            reprogramarToma();
            dismiss();
        });

        return builder.create();
    }

    /**
     * 🔹 Registra la toma del medicamento correctamente.
     */
    private void registrarToma() {
        try {
            medicamento.registrarToma(controladorMedicamento);  // ✅ PASAR EL CONTROLADOR
            controladorMedicamento.actualizarMedicamento(medicamento.getNombre(), medicamento); // ✅ Guardar cambios
            listener.onTomaRegistrada();

            Toast.makeText(getActivity(), "✅ Toma registrada correctamente.", Toast.LENGTH_SHORT).show();
        } catch (IllegalStateException e) {
            Toast.makeText(getActivity(), "⚠️ No hay suficiente cantidad disponible.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 🔹 Omitir la toma del medicamento (Solo guarda en el historial, no afecta cantidad).
     */
    private void omitirToma() {
        medicamento.omitirToma(controladorMedicamento);  // ✅ PASAR EL CONTROLADOR
        controladorMedicamento.actualizarMedicamento(medicamento.getNombre(), medicamento); // ✅ Guardar cambios
        listener.onTomaRegistrada();

        Toast.makeText(getActivity(), "⏭️ Toma omitida.", Toast.LENGTH_SHORT).show();
    }

    /**
     * 🔹 Reprogramar la toma del medicamento con selector de hora.
     */
    private void reprogramarToma() {
        Calendar calendario = Calendar.getInstance();
        int hora = calendario.get(Calendar.HOUR_OF_DAY);
        int minuto = calendario.get(Calendar.MINUTE);

        TimePickerDialog timePicker = new TimePickerDialog(getActivity(), (view, horaSeleccionada, minutoSeleccionado) -> {
            String nuevaHora = String.format("%02d:%02d", horaSeleccionada, minutoSeleccionado);
            medicamento.reprogramarToma(nuevaHora,controladorMedicamento); // ✅ PASAR EL CONTROLADOR
            controladorMedicamento.actualizarMedicamento(medicamento.getNombre(), medicamento); // ✅ Guardar cambios
            listener.onTomaRegistrada();

            Toast.makeText(getActivity(), "🔄 Toma reprogramada para " + nuevaHora, Toast.LENGTH_SHORT).show();
        }, hora, minuto, true);

        timePicker.show();
    }
}






