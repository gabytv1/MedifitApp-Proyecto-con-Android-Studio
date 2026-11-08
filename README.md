# Proyecto de Gestión de Salud - POO (Grupo 2)

Aplicación móvil nativa en Android para el control y seguimiento de la salud personal y familiar. Este proyecto fue desarrollado como parte de la materia de Programación Orientada a Objetos (Término 2024-II).

## 📋 Descripción
La aplicación permite administrar múltiples perfiles de usuario, cada uno con su propio almacenamiento de datos independiente. Facilita el registro y control de medicamentos, programación de citas médicas, seguimiento de actividad física y creación de notas de salud, utilizando un sistema de persistencia de datos local mediante serialización de objetos en archivos `.dat`.

## ✨ Características Principales
El sistema está dividido en módulos independientes para cada perfil:

* **Gestión de Perfiles:** Creación, selección y eliminación de perfiles de usuario, cada uno con su información aislada.
* **Control de Medicamentos:** Registro de medicinas, control de inventario (stock y umbrales de escasez), registro de tomas con historial detallado, opciones para reprogramar tomas y alertas de medicamentos agotados.
* **Directorio Médico:** Registro de doctores con su especialidad, teléfono, correo y dirección.
* **Citas Médicas:** Programación de citas asociadas a un médico, utilizando `DatePickerDialog` y `TimePickerDialog` para una selección intuitiva de fecha y hora.
* **Actividad Física:** Registro de rutinas indicando la fecha, tipo de actividad, duración (en minutos) y horario.
* **Notas de Salud:** Bitácora rápida para registrar síntomas o recordatorios, ordenadas automáticamente por fecha y hora.

## 🛠️ Tecnologías y Arquitectura
* **Lenguaje:** Java.
* **Plataforma:** Android (SDK nativo).
* **Patrón de Diseño:** MVC (Modelo-Vista-Controlador).
* **Almacenamiento:** Persistencia de datos local mediante el uso de flujos de entrada/salida (`FileInputStream`, `FileOutputStream`) y la interfaz `Serializable`. Cada perfil guarda sus datos en archivos `.dat` individuales (ej. `[nombre]_medicamentos.dat`, `[nombre]_citas_medicas.dat`).

## 📦 Estructura del Código
El proyecto está organizado en tres paquetes principales:
* **Modelo:** Clases base que implementan `Serializable` (`Perfil`, `Medicamento`, `CitaMedica`, `ActividadFisica`, `Medico`, `Nota`).
* **Controlador:** Clases encargadas de la lógica de negocio y la lectura/escritura de los archivos locales (ej. `ControladorPerfil`, `ControladorMedicamento`).
* **Vista:** Actividades e interfaces de usuario (XML y clases de Activity).

## 👥 Autores
Desarrollado por el Grupo #2:
* Homero Manuel Avellán Bravo
* Steven Eduardo Noriega Barzola
* Fátima Gabriela Torres Vera
* Jack Luigi Vera Macías
