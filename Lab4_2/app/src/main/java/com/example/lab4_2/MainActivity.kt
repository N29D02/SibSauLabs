package com.example.lab4_2

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.lab4_2.ui.theme.Lab4_2Theme
import com.example.lab4_2.ui.theme.QuizQuestions



class MainActivity : ComponentActivity() {

    private val quizViewModel: QuizViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Lab4_2Widget(quizViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun Lab4_2Widget(viewModel: QuizViewModel){
    val quizQuestions = QuizQuestions()
    val context = LocalContext.current

    Lab4_2Theme {
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
                if (viewModel.isGameEnded){
                    Text("Количество правильных ответов: ${viewModel.correctAnswersCount}", Modifier.padding(top = 24.dp))

                    Button(modifier = Modifier, onClick = {
                        viewModel.resetGame()
                    }) {
                        Text("Повторить?")
                    }
                }
                else{
                    if (viewModel.isGameStarted){
                        Text(viewModel.getCurrentQuestion(), Modifier.padding(top = 24.dp))

                        Row(
                            Modifier
                                .padding(innerPadding)
                                .padding(horizontal = 12.dp)
                                .fillMaxWidth(),

                            ){
                            if (!viewModel.keepingAnswerState){
                                Button(modifier = Modifier, onClick = {
                                    viewModel.makeAnswer(true)
                                }) {
                                    Text("Правда")
                                }
                            }

                            Spacer(modifier = Modifier.weight(1f))

                            Column(){
                                if (!viewModel.keepingAnswerState) {
                                    Button(modifier = Modifier, onClick = {
                                        viewModel.makeAnswer(false)
                                    }) {
                                        Text("Ложь")
                                    }
                                }

                                if (viewModel.keepingAnswerState){
                                    Button(modifier = Modifier, onClick = {
                                        viewModel.getNextQuestion()
                                    }) {
                                        Text("Далее")
                                    }
                                }
                            }
                        }
                        Button(modifier = Modifier, onClick = {
                            val intent = Intent(context, CheatActivity::class.java).apply {
                                putExtra("currentQuestionId", viewModel.currentQuestionId)
                            }
                            context.startActivity(intent)
                        }) {
                            Text("Подсказка")
                        }
                    }

                    if (!viewModel.isGameStarted){
                        Button(modifier = Modifier, onClick = {
                            viewModel.setGameStarted()
                        }) {
                            Text("Начать")
                        }
                    }
                }
            }
        }
    }
}