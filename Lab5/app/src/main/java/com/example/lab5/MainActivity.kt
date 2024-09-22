package com.example.lab5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.lab5.ui.theme.Lab5Theme

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainActivityVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainWidget()


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainWidget(){
    //val mainViewModel: MainActivityVM by ComponentActivity().viewModels()
    var mainViewModel_hourValue = ""
    var mainViewModel_discountValue = 0.1f
    Lab5Theme {
        TopAppBar(title = { Text("DiscountApp") })
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                TextField(value = mainViewModel_hourValue, onValueChange = {

                })
                Text("Количество часов")
                Slider(value = mainViewModel_discountValue, modifier = Modifier.padding(horizontal = 32.dp), onValueChange = {
                    
                })
                Text("Скидка: $mainViewModel_discountValue")
                Button(onClick = {}){
                    Text("Расчитать")
                }

            }
        }
    }
}


@Composable
@Preview

fun MainWidgetPreview(){
    //val mainViewModel: MainActivityVM by ComponentActivity().viewModels()
    MainWidget()
}