package me.partypronl.epoch.ui.screens.home

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.partypronl.epoch.data.models.TimerModel
import me.partypronl.epoch.viewmodel.HomeViewModel

@Composable
fun DeleteTimerDialog(timer: TimerModel, onClose: () -> Unit, viewModel: HomeViewModel = hiltViewModel()) {
    val scope = rememberCoroutineScope()

    AlertDialog(
        title = { Text("Delete timer?") },
        text = { Text("Are you sure you want to delete timer ${timer.name}? This action cannot be undone.") },
        onDismissRequest = onClose,
        dismissButton = { TextButton(
            onClick = onClose
        ) { Text("Cancel") } },
        confirmButton = { TextButton(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    viewModel.deleteTimer(timer.id)
                    onClose()
                    viewModel.updateTimers()
                }
            }
        ) { Text("Delete") } }
    )
}