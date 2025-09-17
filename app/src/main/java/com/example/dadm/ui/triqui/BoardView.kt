package com.example.dadm.ui.triqui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.dadm.R

class BoardView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    companion object {
        const val GRID_WIDTH = 6f
    }

    private var mGame: TicTacToeGame? = null
    private lateinit var mHumanBitmap: Bitmap
    private lateinit var mComputerBitmap: Bitmap
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        initialize(context)
    }

    // Carga de imágenes para X y O
    private fun initialize(context: Context) {
        mHumanBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.x_img)
        mComputerBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.o_img)
    }

    // Permite al fragmento pasarle el estado actual del juego
    fun setGame(game: TicTacToeGame) {
        mGame = game
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val boardWidth = width
        val boardHeight = height

        // Dibuja líneas del tablero
        mPaint.color = Color.LTGRAY
        mPaint.strokeWidth = GRID_WIDTH

        val cellWidth = boardWidth / 3
        val cellHeight = boardHeight / 3

        // Verticales
        canvas.drawLine(cellWidth.toFloat(), 0f, cellWidth.toFloat(), boardHeight.toFloat(), mPaint)
        canvas.drawLine((cellWidth * 2).toFloat(), 0f, (cellWidth * 2).toFloat(), boardHeight.toFloat(), mPaint)
        // Horizontales
        canvas.drawLine(0f, cellHeight.toFloat(), boardWidth.toFloat(), cellHeight.toFloat(), mPaint)
        canvas.drawLine(0f, (cellHeight * 2).toFloat(), boardWidth.toFloat(), (cellHeight * 2).toFloat(), mPaint)

        // Dibuja X y O en las celdas
        for (i in 0 until TicTacToeGame.BOARD_SIZE) {
            val col = i % 3
            val row = i / 3

            val left = col * cellWidth + GRID_WIDTH.toInt()
            val top = row * cellHeight + GRID_WIDTH.toInt()
            val right = (col + 1) * cellWidth - GRID_WIDTH.toInt()
            val bottom = (row + 1) * cellHeight - GRID_WIDTH.toInt()
            val dest = Rect(left, top, right, bottom)

            val game = mGame
            if (game != null) {
                val value = game.getBoardOccupant(i)
                if (value == TicTacToeGame.HUMAN_PLAYER) {
                    canvas.drawBitmap(mHumanBitmap, null, dest, null)
                } else if (value == TicTacToeGame.COMPUTER_PLAYER) {
                    canvas.drawBitmap(mComputerBitmap, null, dest, null)
                }
            }
        }
    }

    // Métodos útiles para detectar toques
    fun getBoardCellWidth(): Int = width / 3
    fun getBoardCellHeight(): Int = height / 3
}