package me.partypronl.epoch.ui.screens.home

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import me.partypronl.epoch.viewmodel.CreateTimerViewModel
import androidx.compose.runtime.setValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTimerSheet(
    sheetState: SheetState,
    showBottomSheet: Boolean,
    onDismissRequest: () -> Unit,
    onFinish: () -> Unit,
    viewModel: CreateTimerViewModel = hiltViewModel()
) {
    val name by viewModel.name.collectAsState()
    val ends by viewModel.ends.collectAsState()
    val canCreate by viewModel.canCreate.collectAsState()
    val creating by viewModel.creating.collectAsState()

    val scope = rememberCoroutineScope()

    if(showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissRequest,
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { viewModel.setName(it) },
                    label = { Text("Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                var showDateModal by remember { mutableStateOf(false) }
                OutlinedTextField(
                    value = ends?.let { convertMillisToDate(it) } ?: "",
                    onValueChange = {},
                    label = { Text("Ends at") },
                    trailingIcon = {
                        Icon(Icons.Default.DateRange, "Select date")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(ends) {
                            awaitEachGesture {
                                awaitFirstDown(pass = PointerEventPass.Initial)
                                val upEvent = waitForUpOrCancellation(pass = PointerEventPass.Initial)
                                if (upEvent != null) {
                                    showDateModal = true
                                }
                            }
                        }
                )

                if(showDateModal) {
                    DatePickerModal(
                        onDateSelected = { viewModel.setEnds(it) },
                        onDismiss = { showDateModal = false }
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = {
                            scope.launch(Dispatchers.IO) {
                                viewModel.create()
                                onFinish()
                            }
                        },
                        enabled = canCreate && !creating
                    ) {
                        Text("Create")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModal(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState()

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
        }
    ) {
        DatePicker(state = datePickerState)
    }
}

fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    return formatter.format(Date(millis))
}