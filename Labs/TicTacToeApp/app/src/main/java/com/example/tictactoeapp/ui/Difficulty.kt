package com.example.tictactoeapp.ui

import com.example.tictactoeapp.ai.AiStrategy
import com.example.tictactoeapp.ai.EasyStrategy
import com.example.tictactoeapp.ai.HardStrategy
import com.example.tictactoeapp.ai.MediumStrategy

enum class Difficulty {
    EASY, MEDIUM, HARD
}

fun strategyFor(difficulty: Difficulty): AiStrategy {
    return when (difficulty) {
        Difficulty.EASY -> EasyStrategy()
        Difficulty.MEDIUM -> MediumStrategy()
        Difficulty.HARD -> HardStrategy()
    }
}
