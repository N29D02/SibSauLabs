package com.example.lab4_2

import android.app.Activity
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
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab4_2.ui.theme.Lab4_2Theme
import com.example.lab4_2.ui.theme.QuizQuestions

class CheatActivity : ComponentActivity() {
    private val cheatViewModel: CheatViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val currentQuestionId = intent.getIntExtra("currentQuestionId", 0)
        cheatViewModel.currentQuestionId = currentQuestionId

        setContent {
            CheatWidget(cheatViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun CheatWidget(viewModel: CheatViewModel){
    val context = LocalContext.current
    Lab4_2Theme {
        Scaffold(Modifier.fillMaxSize(), topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
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
                horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(text = if (viewModel.isCheatShow) "Правильный ответ: ${viewModel.getCheat()}" else "Вы уверены что хотите сделать это?", Modifier.padding(top = 24.dp))

                Button(modifier = Modifier, onClick =
                {
                    if (viewModel.isCheatShow)
                        (context as Activity).finish()
                    else
                        viewModel.setCheatShow()
                })
                {
                    Text(text = if (viewModel.isCheatShow) "Вернуться" else "Показать")
                }

                Text(text = "Версия Android API: ${viewModel.getAPILevel()}", Modifier.padding(top = 12.dp))
            }
        }
    }
}


