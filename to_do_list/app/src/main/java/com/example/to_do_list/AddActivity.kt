package com.example.to_do_list

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.to_do_list.ui.theme.To_do_listTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AddActivity : ComponentActivity() {

    private lateinit var database: AppDatabase
    private lateinit var taskDao: TaskDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        database = AppDatabase.getDatabase(applicationContext)
        taskDao = database.taskDao()

        setContent {
            To_do_listTheme {
                AddTaskScreen(taskDao)
            }
        }
    }
}

@Composable
fun AddTaskScreen(taskDao: TaskDao) {
    val context = LocalContext.current
    var taskDescription by remember { mutableStateOf("") }
    var taskPriority by remember { mutableIntStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxSize()
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
        Row {
            RadioButton(
                selected = taskPriority == 1,
                onClick = { taskPriority = 1 }
            )
            Text(text = "Priority 1", modifier = Modifier.padding(start = 8.dp))
            RadioButton(
                selected = taskPriority == 2,
                onClick = { taskPriority = 2 }
            )
            Text(text = "Priority 2", modifier = Modifier.padding(start = 8.dp))
            RadioButton(
                selected = taskPriority == 3,
                onClick = { taskPriority = 3 }
            )
            Text(text = "Priority 3", modifier = Modifier.padding(start = 8.dp))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (taskDescription.isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        taskDao.insertTask(Task(description = taskDescription, priority = taskPriority))
                    }
                    (context as? Activity)?.finish()
                }
            }
        ) {
            Text("Add Task")
        }
    }
}