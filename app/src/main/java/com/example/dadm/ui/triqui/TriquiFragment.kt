package com.example.dadm.ui.triqui

import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
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

    private lateinit var prefs: SharedPreferences

    private var humanMediaPlayer: MediaPlayer? = null
    private var computerMediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val root = inflater.inflate(R.layout.fragment_triqui, container, false)
        // Recupera los puntajes y dificultad de las preferencias
        prefs = requireActivity().getSharedPreferences("ttt_prefs", Context.MODE_PRIVATE)
        humanWins = prefs.getInt("humanWins", 0)
        androidWins = prefs.getInt("androidWins", 0)
        ties = prefs.getInt("ties", 0)

        mGame = TicTacToeGame()
        val diffOrdinal = prefs.getInt("difficulty", TicTacToeGame.DifficultyLevel.Expert.ordinal)
        mGame.setDifficultyLevel(TicTacToeGame.DifficultyLevel.values()[diffOrdinal])

        mBoardView = root.findViewById(R.id.board)
        mBoardView.setGame(mGame)
        mInfoTextView = root.findViewById(R.id.information)

        updateScores(root)

        root.findViewById<Button>(R.id.btnReset).setOnClickListener { startNewGame() }
        root.findViewById<Button>(R.id.btnDifficulty).setOnClickListener { showDifficultyDialog() }

        // **BOTÓN RESET SCORES**
        root.findViewById<Button>(R.id.btnResetScores).setOnClickListener {
            humanWins = 0
            androidWins = 0
            ties = 0
            prefs.edit()
                .putInt("humanWins", 0)
                .putInt("androidWins", 0)
                .putInt("ties", 0)
                .apply()
            updateScores(root)
            Toast.makeText(requireContext(), "Marcador reiniciado", Toast.LENGTH_SHORT).show()
        }

        // Touch
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

        if (savedInstanceState == null) {
            startNewGame()
        } else {
            mGame.setBoardState(savedInstanceState.getCharArray("board"))
            mGameOver = savedInstanceState.getBoolean("mGameOver")
            mInfoTextView.text = savedInstanceState.getCharSequence("info")
            humanStarts = savedInstanceState.getBoolean("humanStarts", true)
            mGame.setDifficultyLevel(
                TicTacToeGame.DifficultyLevel.values()[savedInstanceState.getInt("difficulty", diffOrdinal)]
            )
            updateScores(root)
            mBoardView.invalidate()
            if (!mGameOver && mInfoTextView.text == getString(R.string.turn_computer)) {
                Handler(Looper.getMainLooper()).postDelayed({
                    val move = mGame.getComputerMove()
                    makeMove(TicTacToeGame.COMPUTER_PLAYER, move, isHuman = false)
                }, 500)
            }
        }
        return root
    }

    override fun onResume() {
        super.onResume()
        humanMediaPlayer = MediaPlayer.create(requireContext(), R.raw.move_human)
        computerMediaPlayer = MediaPlayer.create(requireContext(), R.raw.move_computer)
    }

    override fun onPause() {
        super.onPause()
        humanMediaPlayer?.release()
        computerMediaPlayer?.release()
        // Guarda puntajes y dificultad
        prefs.edit()
            .putInt("humanWins", humanWins)
            .putInt("androidWins", androidWins)
            .putInt("ties", ties)
            .putInt("difficulty", mGame.getDifficultyLevel().ordinal)
            .apply()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putCharArray("board", mGame.getBoardState())
        outState.putBoolean("mGameOver", mGameOver)
        outState.putCharSequence("info", mInfoTextView.text)
        outState.putBoolean("humanStarts", humanStarts)
        outState.putInt("difficulty", mGame.getDifficultyLevel().ordinal)
    }

    private fun updateScores(root: View? = view) {
        root?.findViewById<TextView>(R.id.human_score)?.text =
            getString(R.string.score_human, humanWins)
        root?.findViewById<TextView>(R.id.ties_score)?.text =
            getString(R.string.score_tie, ties)
        root?.findViewById<TextView>(R.id.android_score)?.text =
            getString(R.string.score_ia, androidWins)
    }

    private fun startNewGame() {
        mGame.clearBoard()
        mGameOver = false
        mBoardView.invalidate()
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
                prefs.edit().putInt("difficulty", which).apply()
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