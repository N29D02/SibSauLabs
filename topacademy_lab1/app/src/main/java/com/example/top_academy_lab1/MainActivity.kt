package com.example.top_academy_lab1

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Task3()
        }
    }
}

//Вариант 2. Вывести на экран монитора название растения «это Дуб»,
// если введена буква d, независимо от того, большая она или маленькая.

@Composable
fun Task2() {
    val context = LocalContext.current
    var inputString by remember { mutableStateOf("")}

    Scaffold(modifier = Modifier.fillMaxSize()) {  innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center)
        {
            Text("Задание номер 2")
            TextField(
                value = inputString,
                onValueChange = {
                    newText ->
                    if (newText.length <= 1){
                        inputString = newText
                        if (inputString.lowercase().contains('d')){
                            val toast = Toast.makeText(context, "Это Дуб", LENGTH_SHORT)
                            toast.show()
                        }
                    }
                }
            )
        }

    }
}

//Вариант 1. Вывести на экран монитора название дня недели по его номеру.
@Composable
fun Task1() {
    val context = LocalContext.current //Контекст, используется для Toast (всплывающе окна)
    var inputString by remember { mutableStateOf("")} //Умная переменная


    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Text("Задание номер 1")
            TextField(
                value = inputString,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), //Настройка клавиатуры на ввод только чисел
                onValueChange = { newText ->
                    if (newText.length <= 1) {
                        inputString = newText
                        Log.d("test", inputString);

                        if (inputString != "") {
                            val toast = Toast.makeText(
                                context,
                                getDayOfTheWeek(inputString),
                                LENGTH_SHORT
                            ) //Создание текста Toast
                            toast.show() //Отображение Toast
                        }
                    }
                }
            )
        }

    }
}



fun getDayOfTheWeek(dayNumber: String): String {
    return when (dayNumber){
        "1" -> "Зима"
        "2" -> "Весна"
        "3" -> "Лето"
        "4" -> "Осень"
        else -> "Ошибка"
    }
}

//Вариант 3. Площадь ромба можно вычислить двумя способами:
// 1) по высоте и стороне S=ah;
//2) по диагоналям S=(d1*d2)/2.
//Вычислить площадь ромба по номеру варианта решения.

@Composable
fun Task3() {
    val context = LocalContext.current //Контекст, используется для Toast (всплывающе окна)
    var a by remember { mutableStateOf("") } //Умная переменная
    var h by remember { mutableStateOf("") }
    var d1 by remember { mutableStateOf("") }
    var d2 by remember { mutableStateOf("") }

    var result1 by remember { mutableStateOf("") }
    var result2 by remember { mutableStateOf("") }



    Scaffold(modifier = Modifier.fillMaxSize()) {  innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text("Задание номер 3")
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "По высоте и стороне S=a*h = $result1"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "По диагоналям S=(d1*d2)/2 = $result2"
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = a,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), //Настройка клавиатуры на ввод только чисел
                onValueChange = {
                        newText ->
                    a = newText
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = h,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), //Настройка клавиатуры на ввод только чисел
                onValueChange = {
                        newText ->
                    h = newText
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = d1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), //Настройка клавиатуры на ввод только чисел
                onValueChange = {
                        newText ->
                    d1 = newText
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = d2,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number), //Настройка клавиатуры на ввод только чисел
                onValueChange = {
                        newText ->
                    d2 = newText
                }
            )
            Spacer(modifier = Modifier.height(30.dp))
            Button(onClick = {
                result1 = makeFormula1(a,h)
                result2 = makeFormula2(d1,d2)
            }) { Text("Рассчитать") }
        }

    }
}

fun makeFormula1(a: String, h: String): String{
    val int_a = a.toIntOrNull() ?: 0
    val int_h = h.toIntOrNull() ?: 0

    val S = int_a*int_h
    return "$S"
}

fun makeFormula2(d1: String, d2: String): String{
    val int_d1 = d1.toIntOrNull() ?: 0
    val int_d2 = d2.toIntOrNull() ?: 0

    val S = (int_d1*int_d2)/2
    return "$S"
}
