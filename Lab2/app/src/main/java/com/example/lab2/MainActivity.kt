package com.example.lab2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab2.ui.theme.Lab2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab1WidgetPreview()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Lab1WidgetPreview() {
    Lab2Theme {
        val context = LocalContext.current
        var inputMinimalValue by remember { mutableStateOf("") }
        var minimalNumericValue by remember { mutableFloatStateOf(0f) }

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding).fillMaxSize(), verticalArrangement = Arrangement.Center) {
                Text(
                    text = "Lab1",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                )
                TextField(modifier = Modifier
                    .padding(top = 12.dp, start = 36.dp, end = 36.dp)
                    .fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    value = inputMinimalValue,
                    onValueChange = {newText -> minimalNumericValue = newText.toFloat()})

                Button(modifier = Modifier.align(Alignment.CenterHorizontally).padding(12.dp),
                    onClick = {
                        while(true){

                        }
                    }) {
                    Text("Посчитать ряд")
                }
            }
        }
    }
}