package com.example.lab4_2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lab4_2.ui.theme.QuizQuestions

class CheatViewModel: ViewModel() {
    var currentQuestionId by mutableIntStateOf(0)
    var isCheatShow by mutableStateOf(false)

    fun getCheat(): Boolean{
        val quizQuestions = QuizQuestions()
        return quizQuestions.questionsPair[currentQuestionId].second
    }

    fun setCheatShow(){
        isCheatShow = true
    }
}