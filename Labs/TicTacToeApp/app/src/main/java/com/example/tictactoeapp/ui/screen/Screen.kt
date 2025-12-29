package com.example.tictactoeapp.ui.screen

import com.example.tictactoeapp.ui.Difficulty

sealed class Screen {
    object Menu : Screen()
    object PlayModeSelect : Screen()
    data class ChooseSide(val mode: String) : Screen()
    data class GameHumanTraining(val userPlaysAs: Char) : Screen()
    data class GameHumanCompetitive(
        val opponentName: String,
        val userPlaysAs: Char
    ) : Screen()
    data class GameComputer(
        val difficulty: Difficulty,
        val userPlaysAs: Char
    ) : Screen()
    data class DifficultySelect(val userPlaysAs: Char) : Screen()
    data class EnterSecondPlayer(val userPlaysAs: Char) : Screen()
    object Statistics : Screen()
    object Leaderboard : Screen()
    object Settings : Screen()
}
