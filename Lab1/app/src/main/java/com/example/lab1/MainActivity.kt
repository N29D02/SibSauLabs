package com.example.lab1

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
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
            Lab1WidgetPreview()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Lab1WidgetPreview() {
    Lab1Theme {
        val context = LocalContext.current
        var inputString by remember {mutableStateOf("")}

        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(), verticalArrangement = Arrangement.Center) {
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
                value = inputString,
                onValueChange = {newText ->
                    if (newText.length <= 1){
                        inputString = newText
                        if (inputString.lowercase().contains('d')) {
                            val toast = Toast.makeText(context, "Это Дуб", Toast.LENGTH_SHORT)
                            toast.setGravity(Gravity.TOP, 0 ,0)
                            toast.show()
                        }
                    }
                })
            }
        }
    }
}
