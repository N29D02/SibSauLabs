package com.example.lab5

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.lab5.ui.theme.Lab5Theme

class MainActivity : ComponentActivity() {
    private val mainViewModel: MainActivityVM by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainWidget(mainViewModel)


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainWidget(viewModel: MainActivityVM){
    val context = LocalContext.current

    Lab5Theme {
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ),
                title = { Text("DiscountApp") })
        }) { innerPadding ->
            Column(
                Modifier
                    .padding(innerPadding)
                    .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                TextField(value = viewModel.hoursRawValue, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), onValueChange = {
                    viewModel.hoursRawValue = it
                })
                Text("Количество часов (2000р/ч)")
                Slider(value = viewModel.discountValue, modifier = Modifier.padding(horizontal = 32.dp), steps = 100, onValueChange = {
                    viewModel.discountValue = it
                })
                Text("Скидка: ${(viewModel.discountValue*100).toInt()}%")
                Spacer(modifier = Modifier.size(10.dp))
                Button(onClick = {
                    val summaryPrice = viewModel.hoursRawValue.toInt()*2000*(1-viewModel.discountValue)
                    val intent = Intent(context, PaymentActivity::class.java).apply {
                        putExtra("summaryPrice", summaryPrice)
                    }
                    context.startActivity(intent)
                }){
                    Text("Ок")
                }

            }
        }
    }
}