package com.example.lab4

import androidx.compose.ui.res.stringResource

class QuizQuestions {
    val questionsPair: List<Pair<String, Boolean>> = listOf(
        Pair("Москва - столица России?", true),
        Pair("Солнце вращается вокруг Земли?", false),
        Pair("Вода замерзает при температуре 0°C?", true),
        Pair("Сумма углов треугольника равна 180°?", true),
        Pair("Китай находится в Европе?", false)
    )
}
