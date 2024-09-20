package com.example.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab4.ui.theme.Lab4Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab4WidgetPreview()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Lab4WidgetPreview(){

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

                Text("Question example", Modifier.padding(top = 24.dp))
                Row(
                    Modifier
                        .padding(innerPadding)
                        .padding(horizontal = 12.dp)
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Button(modifier = Modifier, onClick = {
                    }) {
                        Text("True")
                    }
                    Column(){
                        Button(modifier = Modifier, onClick = {
                        }) {
                            Text("False")
                        }
                        Button(modifier = Modifier, onClick = {
                        }) {
                            Text("Next")
                        }
                    }


                }

                /*
                Button(modifier = Modifier, onClick = {
                }) {
                    Text("Start")
                }
                */

            }
        }
    }
}