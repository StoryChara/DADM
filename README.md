# Desarrollo de Aplicaciones para Dispositivos Móviles

[Repositorio en GitHub](https://github.com/StoryChara/DADM)

Este repositorio recopila los retos, ejemplos y recursos desarrollados como apoyo para la asignatura de **Desarrollo de Aplicaciones para Dispositivos Móviles** (DADM). El código está organizado en módulos temáticos (paquetes), totalmente en **Kotlin**.

---

## 📁 Estructura general del proyecto

El código fuente se encuentra bajo la carpeta principal `app/`. Allí encontrarás subcarpetas ordenadas por funcionalidad o reto, destacando:

- **/ui/home/**  
  Pantalla de bienvenida con el clásico "Hola Mundo", implementada con Fragment y ViewModel en arquitectura MVVM.  
  (Consulta el [README de ui/home](https://github.com/StoryChara/DADM/blob/main/app/src/main/java/com/example/dadm/ui/home/README.md) para más detalles.)

- **/ui/triqui/**  
  Todo lo necesario para el reto de triqui, incluyendo lógica desacoplada, UI modular, cambio de dificultad y marcador.  
  (Consulta el [README de ui/triqui](https://github.com/StoryChara/DADM/blob/main/app/src/main/java/com/example/dadm/ui/triqui/README.md).)

---

## 🎨 Personalización y elementos comunes

- **Colores personalizados**:  
  La app usa paletas de colores diseñadas especialmente, centralizadas en `res/values/colors.xml`, combinando verdes, naranjas, marrones y azules pastel para una experiencia visual cálida y amigable, alineada con una estética uniforme.

- **Icono de app personalizado**:  
  El icono launcher fue creado y adaptado para representar el proyecto de manera única.

- **Menú lateral (Navigation Drawer)**:  
  Implementado utilizando los componentes de Material, permite acceder de forma estructurada a los diferentes retos.  
  El menú es completamente personalizable, incluye iconos y soporta modo oscuro de forma eficiente gracias a la configuración de paletas en `colors.xml`.

- **Footer global**:  
  Todas las pantallas principales cuentan con un footer fijo que incluye:
  - **Botón de "Salir"**: Permite cerrar la aplicación de inmediato (usando `finishAffinity()`).
  - **Botón de "Créditos"**: Abre un `AlertDialog` con información de autoría, licencia y agradecimientos.

---

### Requerimientos técnicos

- Android Studio Iguana o superior.
- Kotlin >= 1.8
- API mínima sugerida: 23 (Android 6.0)
- Familiaridad con Fragment, ViewModel y navigation components.

---

## 🚀 Créditos

Desarrollado por **María José Jara Herrera** para la asignatura de DADM — 2025.

