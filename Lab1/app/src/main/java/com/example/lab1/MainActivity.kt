package com.example.lab1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab1.ui.theme.Lab1Theme
import java.time.Duration

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab1Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.padding(innerPadding)) {
                        Lab1Widget(
                            name = "Lab1",
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxWidth()
                                .padding(top = 12.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Lab1Widget(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "This is $name!",
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun Lab1WidgetPreview() {
    Lab1Theme {
        val context = LocalContext.current
        var inputString by remember {mutableStateOf("")}

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(modifier = Modifier.padding(innerPadding)) {
                Lab1Widget(
                    name = "Lab1",
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                )
                TextField(modifier = Modifier
                    .padding(top = 12.dp)
                    .fillMaxWidth(),
                value = inputString,
                onValueChange = {newText -> inputString = newText})

                Button(modifier = Modifier.align(Alignment.CenterHorizontally).padding(12.dp),
                onClick = {
                    if (inputString.contains('d')) {
                        Toast.makeText(context, "Это Дуб", Toast.LENGTH_SHORT).show()
                    }
                    else{

                    }
                    }) {
                    Text("Проверить")
                }
            }
        }
    }
}