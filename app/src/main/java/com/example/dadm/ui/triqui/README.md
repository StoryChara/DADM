# Paquete: `ui/triqui` — Juego de Triqui (Tic-Tac-Toe)

Este paquete contiene todos los recursos relacionados con la implementación del reto de triqui (tic-tac-toe) para la materia de Desarrollo de Aplicaciones para Dispositivos Móviles (DADM). Aquí se encapsula la lógica, interfaz y recursos propios de esta pantalla de juego, siguiendo una arquitectura modular y buenas prácticas de Android.

## Estructura de archivos principales

- **TriquiFragment.kt**
  - Fragmento principal que administra el ciclo de vida de la UI del juego.
  - Inicializa el tablero, gestiona el turno, la alternancia de primer jugador, controles de botón para reinicio y dificultad, y muestra el marcador.
  - Despliega un `AlertDialog` para seleccionar la dificultad y actualiza la lógica del juego según la selección.

- **TicTacToeGame.kt**
  - Clase independiente con toda la lógica del juego.
  - Gestiona el tablero, la IA con tres dificultades (`Easy`, `Harder`, `Expert`), chequeo de ganador y movimientos válidos.
  - Sigue la recomendación de separación UI/lógica para máxima facilidad de pruebas y mantenimiento.

- **fragment_triqui.xml**
  - Layout visual de la pantalla, con:
    - Un tablero de 9 botones (3x3) usando `TableLayout`.
    - Un status de turno mediante `TextView`.
    - Una fila horizontal para los botones de “Nuevo Juego” y “Dificultad”.
    - Marcadores de victorias, empates y derrotas en una fila compacta y visualmente clara.
  
## Funcionalidades principales

- Alternancia de quién inicia la partida (humano o Android).
- Selección y cambio de dificultad en vivo (fácil, media, experta) desde la UI con `AlertDialog`.
- Estadísticas de victorias, empates y derrotas visibles y persistentes durante la sesión.
- Reinicio de partida inmediato con botón dedicado.
- Colores y textos accesibles, recursos centralizados en `strings.xml` para facilitar localización.