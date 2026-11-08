package com.example.grupo2_proyecto.Modelo;

import android.content.Context;

import com.example.grupo2_proyecto.Controlador.ControladorMedicamento;
import com.example.grupo2_proyecto.Controlador.ControladorNota;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Perfil implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String apellido;
    private String email;
    private List<Medicamento> medicamentos;
    private List<Medico> medicos;
    private List<CitaMedica> citasMedicas;
    private List<ActividadFisica> actividadesFisicas;
    private List<Nota> notas; // 📌 Nueva lista para manejar las notas

    // 🔹 Constructor
    public Perfil(String nombre, String apellido, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.medicamentos = new ArrayList<>();
        this.medicos = new ArrayList<>();
        this.citasMedicas = new ArrayList<>();
        this.actividadesFisicas = new ArrayList<>();
        this.notas = new ArrayList<>(); // Inicializar lista de notas
    }

    // 📌 Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Medicamento> getMedicamentos() {
        return medicamentos != null ? medicamentos : new ArrayList<>();
    }

    public void setMedicamentos(List<Medicamento> medicamentos) {
        this.medicamentos = medicamentos != null ? new ArrayList<>(medicamentos) : new ArrayList<>();
    }

    public List<Medico> getMedicos() {
        return medicos != null ? new ArrayList<>(medicos) : new ArrayList<>();
    }

    public void setMedicos(List<Medico> medicos) {
        this.medicos = medicos != null ? new ArrayList<>(medicos) : new ArrayList<>();
    }

    public List<CitaMedica> getCitasMedicas() {
        return citasMedicas != null ? citasMedicas : new ArrayList<>();
    }

    public void setCitasMedicas(List<CitaMedica> citasMedicas) {
        this.citasMedicas = citasMedicas != null ? new ArrayList<>(citasMedicas) : new ArrayList<>();
    }

    public List<ActividadFisica> getActividadesFisicas() {
        return actividadesFisicas != null ? actividadesFisicas : new ArrayList<>();
    }

    public void setActividadesFisicas(List<ActividadFisica> actividadesFisicas) {
        this.actividadesFisicas = actividadesFisicas != null ? new ArrayList<>(actividadesFisicas) : new ArrayList<>();
    }

    // 📌 Métodos para manejar Medicamentos con Persistencia
    public void agregarMedicamento(Medicamento medicamento, Context context) {
        if (medicamento != null && !medicamentos.contains(medicamento)) {
            medicamentos.add(medicamento);
            guardarMedicamentos(context); // Guardar cambios
        }
    }

    public void agregarMedico(Medico medico) {
        if (medico != null && !medicos.contains(medico)) {
            medicos.add(medico);
        }
    }

    public boolean eliminarMedicamento(String nombre, Context context) {
        boolean eliminado = medicamentos.removeIf(med -> med.getNombre().equalsIgnoreCase(nombre));
        if (eliminado) {
            guardarMedicamentos(context);
        }
        return eliminado;
    }

    // 📌 Métodos para Citas Médicas
    public void agregarCita(CitaMedica cita) {
        if (cita != null) {
            if (citasMedicas == null) {
                citasMedicas = new ArrayList<>();
            }
            citasMedicas.add(cita);
        }
    }

    public boolean eliminarCitaMedica(String tituloCita) {
        if (citasMedicas != null) {
            return citasMedicas.removeIf(cita -> cita.getTitulo().equalsIgnoreCase(tituloCita));
        }
        return false;
    }

    // 📌 Métodos para Actividades Físicas
    public void agregarActividadFisica(ActividadFisica actividad) {
        if (actividad != null) {
            if (actividadesFisicas == null) {
                actividadesFisicas = new ArrayList<>();
            }
            actividadesFisicas.add(actividad);
        }
    }

    public boolean eliminarActividadFisica(String actividadNombre) {
        if (actividadesFisicas != null) {
            return actividadesFisicas.removeIf(a -> a.getActividad().equalsIgnoreCase(actividadNombre));
        }
        return false;
    }

    // 📌 Guardar los medicamentos en almacenamiento usando el ControladorMedicamento
    public void guardarMedicamentos(Context context) {
        ControladorMedicamento controlador = new ControladorMedicamento(context, this.nombre);
        controlador.guardarMedicamentos();
    }

    // 📌 Cargar medicamentos desde almacenamiento al iniciar sesión en el perfil
    public void cargarMedicamentos(Context context) {
        ControladorMedicamento controlador = new ControladorMedicamento(context, this.nombre);
        this.medicamentos = controlador.obtenerMedicamentos();
    }

    // 📌 Guardar todo el perfil en almacenamiento
    public void guardarPerfil(Context context) {
        guardarMedicamentos(context);
        guardarNotas(context);
    }

    // 📌 Métodos para manejo de Notas con Persistencia
    public List<Nota> getNotas() {
        return notas != null ? notas : new ArrayList<>();
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas != null ? new ArrayList<>(notas) : new ArrayList<>();
    }

    public void agregarNota(String descripcion, Context context) {
        Nota nuevaNota = new Nota(descripcion);
        notas.add(nuevaNota);
        ControladorNota controlador = new ControladorNota(context, this.nombre);
        controlador.agregarNota(descripcion);
    }

    public boolean eliminarNota(String descripcion, Context context) {
        boolean eliminado = notas.removeIf(nota -> nota.getDescripcion().equalsIgnoreCase(descripcion));
        if (eliminado) {
            guardarNotas(context);
        }
        return eliminado;
    }

    // 📌 Guardar notas en almacenamiento usando el ControladorNota
    public void guardarNotas(Context context) {
        ControladorNota controladorNota = new ControladorNota(context, this.nombre);
        controladorNota.guardarNotas();
    }

    // 📌 Cargar notas desde almacenamiento al iniciar sesión en el perfil
    public void cargarNotas(Context context) {
        ControladorNota controladorNota = new ControladorNota(context, this.nombre);
        this.notas = controladorNota.obtenerNotas();
    }

    // 📌 Métodos de comparación y representación
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Perfil perfil = (Perfil) o;
        return Objects.equals(nombre, perfil.nombre) &&
                Objects.equals(apellido, perfil.apellido) &&
                Objects.equals(email, perfil.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellido, email);
    }

    @Override
    public String toString() {
        return "Perfil: " + nombre + " " + apellido + "\n" +
                "Email: " + email + "\n" +
                "Medicamentos: " + (medicamentos.isEmpty() ? "Ninguno" : medicamentos.size()) + "\n" +
                "Médicos: " + (medicos.isEmpty() ? "Ninguno" : medicos.size()) + "\n" +
                "Citas Médicas: " + (citasMedicas.isEmpty() ? "Ninguna" : citasMedicas.size()) + "\n" +
                "Actividades Físicas: " + (actividadesFisicas.isEmpty() ? "Ninguna" : actividadesFisicas.size()) + "\n" +
                "Notas: " + (notas.isEmpty() ? "Ninguna" : notas.size());
    }
}
