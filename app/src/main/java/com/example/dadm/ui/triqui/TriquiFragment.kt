package com.example.dadm.ui.triqui

import android.os.Bundle
import android.view.LayoutInflater
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
    private lateinit var mBoardButtons: Array<Button>
    private lateinit var mInfoTextView: TextView

    private var mGameOver = false
    private var humanWins = 0
    private var androidWins = 0
    private var ties = 0
    private var humanStarts = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_triqui, container, false)

        // Botón de nuevo juego
        val btnReset = root.findViewById<Button>(R.id.btnReset)
        btnReset.setOnClickListener { startNewGame() }

        // Botón de dificultad
        val btnDifficulty = root.findViewById<Button>(R.id.btnDifficulty)
        btnDifficulty.setOnClickListener { showDifficultyDialog() }

        mBoardButtons = arrayOf(
            root.findViewById(R.id.btn1),
            root.findViewById(R.id.btn2),
            root.findViewById(R.id.btn3),
            root.findViewById(R.id.btn4),
            root.findViewById(R.id.btn5),
            root.findViewById(R.id.btn6),
            root.findViewById(R.id.btn7),
            root.findViewById(R.id.btn8),
            root.findViewById(R.id.btn9)
        )
        mInfoTextView = root.findViewById(R.id.information)
        mGame = TicTacToeGame()

        // ¡Inicializa correctamente los textos de puntaje desde el inicio!
        root.findViewById<TextView>(R.id.human_score).text = getString(R.string.score_human, humanWins)
        root.findViewById<TextView>(R.id.ties_score).text = getString(R.string.score_tie, ties)
        root.findViewById<TextView>(R.id.android_score).text = getString(R.string.score_ia, androidWins)

        startNewGame()
        return root
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
        for ((i, btn) in mBoardButtons.withIndex()) {
            btn.text = ""
            btn.isEnabled = true
            btn.setOnClickListener { onHumanMove(i) }
        }
        if (humanStarts) {
            mInfoTextView.text = getString(R.string.first_human)
        } else {
            mInfoTextView.text = getString(R.string.turn_computer)
            val move = mGame.getComputerMove()
            setMove(TicTacToeGame.COMPUTER_PLAYER, move)
        }
        humanStarts = !humanStarts
        updateScores()
    }

    private fun onHumanMove(location: Int) {
        if (!mGameOver && mBoardButtons[location].isEnabled) {
            setMove(TicTacToeGame.HUMAN_PLAYER, location)
            var winner = mGame.checkForWinner()
            if (winner == 0) {
                mInfoTextView.text = getString(R.string.turn_computer)
                val move = mGame.getComputerMove()
                setMove(TicTacToeGame.COMPUTER_PLAYER, move)
                winner = mGame.checkForWinner()
            }
            updateStatus(winner)
        }
    }

    private fun setMove(player: Char, location: Int) {
        mGame.setMove(player, location)
        mBoardButtons[location].isEnabled = false
        mBoardButtons[location].text = player.toString()
        mBoardButtons[location].setTextColor(
            if (player == TicTacToeGame.HUMAN_PLAYER)
                resources.getColor(android.R.color.holo_green_dark)
            else
                resources.getColor(android.R.color.holo_red_dark)
        )
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