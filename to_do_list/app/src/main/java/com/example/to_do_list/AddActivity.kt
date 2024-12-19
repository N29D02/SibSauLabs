package com.example.to_do_list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.to_do_list.ui.theme.To_do_listTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(taskDao: TaskDao, navController: NavController) {
    val context = LocalContext.current
    var taskDescription by remember { mutableStateOf("") }
    var taskPriority by remember { mutableIntStateOf(1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Task") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = taskDescription,
                onValueChange = { taskDescription = it },
                label = { Text("Task Description") }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Priority")
            Spacer(modifier = Modifier.height(16.dp))
            Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = taskPriority == 1,
                    onClick = { taskPriority = 1},
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFEF9A9A))
                )
                Text(text = "High", modifier = Modifier.padding(start = 8.dp))
                RadioButton(
                    selected = taskPriority == 2,
                    onClick = { taskPriority = 2 },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFFFCC80))
                )
                Text(text = "Medium", modifier = Modifier.padding(start = 8.dp))
                RadioButton(
                    selected = taskPriority == 3,
                    onClick = { taskPriority = 3 },
                    colors = RadioButtonDefaults.colors(selectedColor = Color(0xFFA5D6A7))
                )
                Text(text = "Low", modifier = Modifier.padding(start = 8.dp))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (taskDescription.isNotEmpty()) {
                        CoroutineScope(Dispatchers.IO).launch {
                            taskDao.insertTask(Task(description = taskDescription, priority = taskPriority))
                        }
                        navController.popBackStack()
                    }
                }
            ) {
                Text("Add Task")
            }
        }
    }
}