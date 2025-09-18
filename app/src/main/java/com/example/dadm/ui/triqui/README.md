# Paquete: `ui/triqui` — Juego de Triqui (Tic-Tac-Toe)

Este paquete contiene todos los recursos relacionados con la implementación completa y mejorada del reto de triqui (tic-tac-toe) para la materia de Desarrollo de Aplicaciones para Dispositivos Móviles (DADM). Abarca la lógica, la interfaz, la interacción táctil, los elementos visuales personalizados y los efectos de sonido, con estructura modular y buenas prácticas de Android.

---

## Estructura de archivos principales

- **TriquiFragment.kt**
  - Fragmento principal que administra el ciclo de vida de la UI del juego.
  - Inicializa el tablero gráfico (`BoardView`), maneja turnos, alterna el jugador inicial, botones para reinicio y dificultad, marcador y la integración sonora.
  - Despliega un `AlertDialog` para seleccionar la dificultad y actualiza la lógica del juego según la selección.

- **TicTacToeGame.kt**
  - Lógica independiente y desacoplada del juego.
  - Gestiona el estado interno del tablero, IA con tres niveles de dificultad (`Easy`, `Harder`, `Expert`), checa ganador, movimientos válidos y expone el método `getBoardOccupant(i: Int)` para la vista gráfica.

- **BoardView.kt**
  - Custom View encargado de dibujar el tablero, pintar las fichas (`X` y `O`) usando imágenes (bitmaps), interpretar toques del usuario para jugar, y solicitar redibujos según el estado del juego.
  - Integra el método `setGame()` para recibir el estado solo lectura del juego y métodos para calcular dimensiones de cada celda.

- **fragment_triqui.xml**
  - Layout visual del fragmento, contiene:
    - El `BoardView` integrado como tablero central (reemplaza completamente al anterior TableLayout de botones).
    - Un TextView para mostrar turno/status.
    - Dos botones horizontales: “Nuevo Juego” y “Dificultad”.
    - Un marcador alineado horizontalmente para victorias, empates y derrotas.

- **/res/drawable/x_img.png & o_img.png**
  - Imágenes de las fichas X y O, usadas por BoardView para mostrar los movimientos.

- **/res/raw/move_human.mp3 & move_computer.mp3**
  - Efectos de sonido personalizados para los movimientos del humano y de la computadora.

---

## Funcionalidades principales

- **Tablero gráfico interactivo:** El tablero de triqui es ahora completamente gráfico, con celdas y fichas dibujadas dinámicamente.
- **Interacción táctil:** El usuario realiza sus movimientos tocando directamente la celda que desea jugar.
- **Efectos de sonido integrados:** Se reproducen sonidos distintos para el turno humano y el de la IA, mejorando la experiencia y el feedback.
- **Retraso en el turno de la IA:** Añade realismo y claridad, permitiendo ver los mensajes de “turno” en pantalla antes del movimiento del computador.
- **Marcador y reinicio:** El marcador se actualiza en tiempo real y puedes reiniciar el juego en cualquier momento.
- **Selector de dificultad:** Cambia la dificultad de la IA al instante desde la UI, usando un diálogo claro.
