package com.example.lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab2.ui.theme.Lab2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab2WidgetPreview()
        }
    }
}

@Preview
@Composable
fun Lab2WidgetPreview(){
    Lab2Theme {
        var sum by remember { mutableStateOf(0)}
        var inputString by remember { mutableStateOf("")}

        val o_accuracy = 0.00001f

        var nextSum = 1/

        while


        Scaffold(Modifier.fillMaxSize()){ innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center){
                Text(
                    text = "",
                    textAlign =  TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(value = inputString, onValueChange = newText -> inputString = newText)
            }
        }
    }
}


