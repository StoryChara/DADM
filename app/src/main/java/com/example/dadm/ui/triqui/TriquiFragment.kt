package com.example.dadm.ui.triqui

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.dadm.R

class TriquiFragment : Fragment() {

    private lateinit var mGame: TicTacToeGame
    private lateinit var mBoardView: BoardView
    private lateinit var mInfoTextView: TextView

    private var mGameOver = false
    private var humanWins = 0
    private var androidWins = 0
    private var ties = 0
    private var humanStarts = true

    // Sonidos
    private var humanMediaPlayer: MediaPlayer? = null
    private var computerMediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_triqui, container, false)

        mGame = TicTacToeGame()

        mBoardView = root.findViewById(R.id.board)
        mBoardView.setGame(mGame)
        mInfoTextView = root.findViewById(R.id.information)

        // Inicializa el marcador
        root.findViewById<TextView>(R.id.human_score).text = getString(R.string.score_human, humanWins)
        root.findViewById<TextView>(R.id.ties_score).text = getString(R.string.score_tie, ties)
        root.findViewById<TextView>(R.id.android_score).text = getString(R.string.score_ia, androidWins)

        // Botones
        root.findViewById<Button>(R.id.btnReset).setOnClickListener { startNewGame() }
        root.findViewById<Button>(R.id.btnDifficulty).setOnClickListener { showDifficultyDialog() }

        // Listener de toques sobre el tablero
        mBoardView.setOnTouchListener { _, event ->
            if (!mGameOver && event.action == MotionEvent.ACTION_DOWN) {
                val col = (event.x / mBoardView.getBoardCellWidth()).toInt()
                val row = (event.y / mBoardView.getBoardCellHeight()).toInt()
                val pos = row * 3 + col

                if (pos in 0..8 && mGame.getBoardOccupant(pos) == TicTacToeGame.OPEN_SPOT) {
                    makeMove(TicTacToeGame.HUMAN_PLAYER, pos, isHuman = true)
                }
            }
            true
        }

        startNewGame()
        return root
    }

    override fun onResume() {
        super.onResume()
        // Carga los sonidos (ajusta los nombres si tus archivos no son estos)
        humanMediaPlayer = MediaPlayer.create(requireContext(), R.raw.move_human)
        computerMediaPlayer = MediaPlayer.create(requireContext(), R.raw.move_computer)
    }

    override fun onPause() {
        super.onPause()
        humanMediaPlayer?.release()
        computerMediaPlayer?.release()
    }

    private fun updateScores() {
        view?.findViewById<TextView>(R.id.human_score)?.text =
            getString(R.string.score_human, humanWins)
        view?.findViewById<TextView>(R.id.ties_score)?.text =
            getString(R.string.score_tie, ties)
        view?.findViewById<TextView>(R.id.android_score)?.text =
            getString(R.string.score_ia, androidWins)
    }

    private fun startNewGame() {
        mGame.clearBoard()
        mGameOver = false
        mBoardView.invalidate() // Redibuja tablero

        if (humanStarts) {
            mInfoTextView.text = getString(R.string.first_human)
        } else {
            mInfoTextView.text = getString(R.string.turn_computer)
            Handler(Looper.getMainLooper()).postDelayed({
                val move = mGame.getComputerMove()
                makeMove(TicTacToeGame.COMPUTER_PLAYER, move, isHuman = false)
            }, 500)
        }
        humanStarts = !humanStarts
        updateScores()
    }

    // Lógica para hacer un movimiento (humano o computadora)
    private fun makeMove(player: Char, pos: Int, isHuman: Boolean) {
        mGame.setMove(player, pos)
        mBoardView.invalidate()
        if (isHuman) {
            humanMediaPlayer?.start()
        } else {
            computerMediaPlayer?.start()
        }
        val winner = mGame.checkForWinner()
        if (winner == 0 && isHuman) {
            mInfoTextView.text = getString(R.string.turn_computer)
            // Delay antes de movimiento de la computadora
            Handler(Looper.getMainLooper()).postDelayed({
                val move = mGame.getComputerMove()
                makeMove(TicTacToeGame.COMPUTER_PLAYER, move, isHuman = false)
            }, 1000)
        } else {
            updateStatus(winner)
        }
    }

    private fun updateStatus(winner: Int) {
        when (winner) {
            0 -> mInfoTextView.text = getString(R.string.turn_human)
            1 -> {
                mInfoTextView.text = getString(R.string.result_tie)
                ties++
                mGameOver = true
            }
            2 -> {
                mInfoTextView.text = getString(R.string.result_human_wins)
                humanWins++
                mGameOver = true
            }
            3 -> {
                mInfoTextView.text = getString(R.string.result_computer_wins)
                androidWins++
                mGameOver = true
            }
        }
        updateScores()
    }

    private fun showDifficultyDialog() {
        val levels = arrayOf(
            getString(R.string.difficulty_easy),
            getString(R.string.difficulty_harder),
            getString(R.string.difficulty_expert)
        )
        val selected = mGame.getDifficultyLevel().ordinal
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.difficulty_choose))
            .setSingleChoiceItems(levels, selected) { dialog, which ->
                mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.values()[which])
                dialog.dismiss()
                Toast.makeText(
                    context,
                    getString(R.string.difficulty_changed, levels[which]),
                    Toast.LENGTH_SHORT
                ).show()
            }
            .create()
            .show()
    }
}