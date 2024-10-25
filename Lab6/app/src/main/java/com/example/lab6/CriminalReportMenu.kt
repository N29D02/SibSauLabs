package com.example.lab6


import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CriminalReportWidget(viewModel: CriminalReportMenuVM, navController: NavController){

    val context = LocalContext.current
    val selectImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        viewModel.selectedImageUri = uri
    }

    Column(
        Modifier
            .padding(16.dp)
            .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(8.dp) )
    {
        Box(
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(360.dp))
                .background(Color.Gray)
        ) {
            if (viewModel.selectedImageUri != null) {
                Image(
                    painter = rememberAsyncImagePainter(model = viewModel.selectedImageUri),
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            IconButton(modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .background(Color.Black.copy(alpha = 0.2f)), onClick = {
                selectImageLauncher.launch("image/*")
            }){
                Icon(modifier = Modifier.size(36.dp), imageVector = Icons.Outlined.Add, contentDescription = "IconPhotoAdd", tint = Color.White)
            }
        }
        TextField(modifier = Modifier.fillMaxWidth(), value = viewModel.title, onValueChange = {viewModel.title = it}, placeholder = {Text("Title")})
        Text("Details", modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.Start)
            .padding(top = 16.dp))
        Spacer(modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(Color.Black))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start){
            Checkbox(checked = viewModel.solvedCheckState, onCheckedChange = {viewModel.solvedCheckState = !viewModel.solvedCheckState})
            Text("Solved")
        }
        ExposedDropdownMenuBox(expanded = viewModel.expandedState, onExpandedChange = {viewModel.expandedState = !viewModel.expandedState}) {
            TextField(
                value = viewModel.chosenSuspect,
                onValueChange = {},
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = viewModel.expandedState) },
                modifier = Modifier.menuAnchor())
            DropdownMenu(expanded = viewModel.expandedState, onDismissRequest = { viewModel.expandedState = false }) {
                Log.d("test", "${viewModel.suspects}")
                viewModel.suspects.forEach { suspect ->
                    Log.d("test", "${suspect.firstName}")
                    DropdownMenuItem(text = {Text("${suspect.firstName} ${suspect.lastName}")}, onClick = {
                        viewModel.expandedState = false
                        viewModel.chosenSuspect = "${suspect.firstName} ${suspect.lastName}"
                    })
                }
            }
        }

        TextField(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            value = viewModel.selectedDate,
            onValueChange = {},
            readOnly = true,
            placeholder = { Text("Select Date") },
            trailingIcon = {
                IconButton(onClick = { viewModel.showDatePicker = true }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "Select Date")
                }
            }
        )

        if (viewModel.showDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { viewModel.showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        val selectedDate = datePickerState.selectedDateMillis?.let { Date(it) }
                        selectedDate?.let { viewModel.setSelectedDate(it) }
                        viewModel.showDatePicker = false
                    }) {
                        Text("OK")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { viewModel.showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        Button(modifier = Modifier.fillMaxWidth(), onClick = {
            viewModel.addCrime()
            navController.popBackStack()
        }, shape = RoundedCornerShape(8.dp)) {
            Text("Send crime report")
        }
    }
}