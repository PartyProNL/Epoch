package me.partypronl.epoch.ui.util

import android.app.TimePickerDialog
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit,
    selectableDates: SelectableDates? = null
) {
    val datePickerState = rememberDatePickerState(
        selectableDates = selectableDates ?: DatePickerDefaults.AllDates
    )

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        },
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerModal(
    onTimeSelected: (Long) -> Unit,
    onDismiss: () -> Unit
) {
    val state = rememberTimePickerState(
        is24Hour = true
    )

    AlertDialog(
        onDismissRequest = {},
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancel")
            }
        },
        confirmButton = {
            val timeMillis = state.minute * 60 * 1000 + state.hour * 60 * 60 * 1000

            TextButton(onClick = { onTimeSelected(timeMillis.toLong()) }) {
                Text("OK")
            }
        },
        text = {
            TimePicker(
                state = state,
            )
        }
    )
}