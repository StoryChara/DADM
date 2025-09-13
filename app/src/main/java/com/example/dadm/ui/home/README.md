# Paquete: `ui/home` — Pantalla de Inicio (Hello World)

Este módulo contiene la pantalla principal de inicio para la aplicación DADM. Su objetivo es servir como punto de partida y ejemplo básico de arquitectura usando **Fragment**, **ViewModel** y data binding con recursos XML.

## Archivos principales

- **HomeFragment.kt**
  - Fragmento sencillo que muestra una cadena de texto en la pantalla de inicio usando un TextView.
  - Observa datos (LiveData) expuestos por un ViewModel y reacciona a sus cambios en tiempo real.

- **HomeViewModel.kt**
  - ViewModel básico que almacena y expone mediante LiveData la cadena `"Hola Mundo!"`.
  - Puede ser extendido para manejar lógica de negocio o almacenamiento adicional en el futuro.

- **fragment_home.xml**
  - Layout XML para HomeFragment.
  - Usa un `ConstraintLayout` con un TextView centralizado, mostrado con gran tamaño.

## Funcionalidad principal

- **Muestra “Hola Mundo!”** de manera reactiva, usando el patrón MVVM estándar de Android.
- Ejemplo didáctico para la materia de Desarrollo de Aplicaciones para Dispositivos Móviles.
- Funciona como plantilla para agregar nuevas pantallas/fragments en el futuro con buen desacoplamiento UI-lógica.