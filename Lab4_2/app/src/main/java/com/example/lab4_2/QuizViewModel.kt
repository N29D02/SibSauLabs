package com.example.lab4_2

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.lab4_2.ui.theme.QuizQuestions

class QuizViewModel : ViewModel(){
    var currentQuestionId by mutableIntStateOf(0)
    var correctAnswersCount by mutableIntStateOf(0)
    var keepingAnswerState by mutableStateOf(false)
    var isGameStarted by mutableStateOf(false)
    var isGameEnded by mutableStateOf(false)

    val quizQuestions = QuizQuestions()

    fun getCurrentQuestion(): String{
        return quizQuestions.questionsPair[currentQuestionId].first
    }
    fun makeAnswer(answer: Boolean){
        if (quizQuestions.questionsPair[currentQuestionId].second == answer){
            correctAnswersCount++
        }
        keepingAnswerState = true
    }

    fun getNextQuestion(){
        if (currentQuestionId < quizQuestions.questionsPair.count() - 1){
            currentQuestionId++
            keepingAnswerState = false
        }
        else{
            isGameEnded = true
        }
    }

    fun setGameStarted(){
        isGameStarted = true
    }

    fun resetGame(){
        currentQuestionId = 0
        correctAnswersCount = 0
        keepingAnswerState = false
        isGameStarted = false
        isGameEnded = false
    }
}