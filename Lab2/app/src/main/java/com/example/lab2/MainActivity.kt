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
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
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
import java.lang.Math.pow
import kotlin.math.abs
import kotlin.math.pow

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
                TextField(value = inputString, modifier = Modifier.fillMaxWidth(), onValueChange = {newText -> inputString = newText})
                Button(modifier = Modifier.fillMaxWidth(), onClick = {

                    val x = inputString.toInt()
                    var nextSum = 1/x.toDouble()
                    var sum = nextSum
                    val accuracyMultiplicator = 7
                    var counter = 1


                    while (abs(nextSum) > (10.0).pow(-accuracyMultiplicator)){
                        nextSum = (-1).toDouble().pow(counter)/((counter*2-1)*x*x.toDouble().pow((counter * 2 - 1).toDouble()))
                        sum += nextSum
                        counter++
                    }

                    outputString = "Sum = $sum, cycles: $counter, lastSum: $nextSum"
                }) {
                    Text(text = "Calculate")
                }
            }
        }
    }
}

