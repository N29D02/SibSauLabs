package com.example.lab3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab3.ui.theme.Lab3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab3WidgetPreview()
        }
    }
}

@Preview
@Composable
fun Lab3WidgetPreview(){
    Lab3Theme {
        var inputString by remember { mutableStateOf("")}
        var outputString by remember { mutableStateOf("")}

        Scaffold(Modifier.fillMaxSize()){ innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding).padding(horizontal = 88.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center){
                Text(
                    text = outputString,
                    textAlign =  TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(value = inputString, modifier = Modifier.fillMaxWidth(), onValueChange = {
                    newText -> inputString = newText

                    
                })
            }
        }
    }
}