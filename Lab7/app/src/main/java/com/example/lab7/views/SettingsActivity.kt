package com.example.lab7.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.lab7.viewModels.SettingsActivityVM

@Composable
fun SettingsActivity(viewModel: SettingsActivityVM) {
    val useNotification by viewModel.useNotification.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = useNotification,
                onCheckedChange = {
                    viewModel.settingNotifications()
                }
            )
            Text(
                text = "Use Notifications",
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }
}