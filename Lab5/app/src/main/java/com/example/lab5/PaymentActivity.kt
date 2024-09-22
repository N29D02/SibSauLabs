package com.example.lab5

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import com.example.lab5.ui.theme.Lab5Theme

class PaymentActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val currentQuestionId = intent.getFloatExtra("summaryPrice", 0f)
        setContent {
            PaymentWidget(currentQuestionId)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentWidget(currentQuestionId: Float){
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
                Text("Итоговая цена: $currentQuestionId")
                Spacer(modifier = Modifier.size(10.dp))
                Button(onClick = {
                    (context as Activity).finish()
                }){
                    Text("Оплатить")
                }

            }
        }
    }
}