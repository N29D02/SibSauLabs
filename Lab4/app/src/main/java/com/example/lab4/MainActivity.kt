package com.example.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab4.ui.theme.Lab4Theme

class MainActivity : ComponentActivity() {
    private var currentQuestionId = 0
    private var correctAnswersCount = 0
    private var keepingAnswerState = false
    private var isGameStarted = false
    private var isGameEnded = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        savedInstanceState?.let {
            currentQuestionId = it.getInt("currentQuestionId", 0)
            correctAnswersCount = it.getInt("correctAnswersCount", 0)
            keepingAnswerState = it.getBoolean("keepingAnswerState", false)
            isGameStarted = it.getBoolean("isGameStarted", false)
            isGameEnded = it.getBoolean("isGameEnded", false)
        }

        setContent {
            Lab4WidgetPreview()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentQuestionId", currentQuestionId)
        outState.putInt("correctAnswersCount", correctAnswersCount)
        outState.putBoolean("keepingAnswerState", keepingAnswerState)
        outState.putBoolean("isGameStarted", isGameStarted)
        outState.putBoolean("isGameEnded", isGameEnded)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    //@Preview
    @Composable
    fun Lab4WidgetPreview(){
        val quizQuestions = QuizQuestions()

        /*
        var currentQuestionId by rememberSaveable  { mutableIntStateOf(0) }
        var correctAnswersCount by rememberSaveable  { mutableIntStateOf(0) }
        var keepingAnswerState by rememberSaveable  { mutableStateOf(false) }
        var isGameStarted by rememberSaveable  { mutableStateOf(false) }
        var isGameEnded by rememberSaveable  { mutableStateOf(false) }
        */

        Lab4Theme {
            Scaffold(Modifier.fillMaxSize(), topBar = {
                TopAppBar(colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                    title = { Text("GeoQuiz") })

            }){ innerPadding ->
                Column(
                    Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 12.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    if (isGameEnded){
                        Text("Количество правильных ответов: $correctAnswersCount", Modifier.padding(top = 24.dp))

                        Button(modifier = Modifier, onClick = {
                            currentQuestionId = 0
                            correctAnswersCount = 0
                            keepingAnswerState = false
                            isGameStarted = false
                            isGameEnded = false
                            setContent { Lab4WidgetPreview() }
                        }) {
                            Text("Повторить?")
                        }
                    }
                    else{
                        if (isGameStarted){
                            Text(quizQuestions.questionsPair[currentQuestionId].first, Modifier.padding(top = 24.dp))

                            Row(
                                Modifier
                                    .padding(innerPadding)
                                    .padding(horizontal = 12.dp)
                                    .fillMaxSize(),

                                ){
                                if (!keepingAnswerState){
                                    Button(modifier = Modifier, onClick = {
                                        if (quizQuestions.questionsPair[currentQuestionId].second){
                                            correctAnswersCount++
                                        }
                                        keepingAnswerState = true
                                        setContent { Lab4WidgetPreview() }
                                    }) {
                                        Text("Правда")
                                    }
                                }

                                Spacer(modifier = Modifier.weight(1f))

                                Column(){
                                    if (!keepingAnswerState) {
                                        Button(modifier = Modifier, onClick = {
                                            if (!quizQuestions.questionsPair[currentQuestionId].second) {
                                                correctAnswersCount++
                                            }

                                            keepingAnswerState = true
                                            setContent { Lab4WidgetPreview() }
                                        }) {
                                            Text("Ложь")
                                        }
                                    }

                                    if (keepingAnswerState){
                                        Button(modifier = Modifier, onClick = {
                                            if (currentQuestionId < quizQuestions.questionsPair.count() - 1){
                                                currentQuestionId++
                                                keepingAnswerState = false
                                                setContent { Lab4WidgetPreview() }
                                            }
                                            else{
                                                isGameEnded = true
                                                setContent { Lab4WidgetPreview() }
                                            }
                                        }) {
                                            Text("Далее")
                                        }
                                    }
                                }
                            }
                        }

                        if (!isGameStarted){
                            Button(modifier = Modifier, onClick = {
                                isGameStarted = true
                                setContent { Lab4WidgetPreview() }
                            }) {
                                Text("Начать")
                            }
                        }
                    }
                }
            }
        }
    }
}

