package me.partypronl.epoch.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.partypronl.epoch.viewmodel.home.HomeViewModel
import me.partypronl.epoch.R
import me.partypronl.epoch.data.models.TimerModel
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import me.partypronl.epoch.viewmodel.home.HomeEvent
import me.partypronl.epoch.viewmodel.home.HomeEventType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val loadingTimers by homeViewModel.loadingTimers.collectAsState()
    val timers by homeViewModel.timers.collectAsState()

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        homeViewModel.snackbarNotifications.collect { event ->
            scope.launch {
                showSnackbar(
                    event = event,
                    snackbarHostState = snackbarHostState,
                    onUndoDelete = { homeViewModel.undoDelete(it) }
                )
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            CreateTimerFAB { showBottomSheet = true }
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        if(loadingTimers) {
            LoadingTimers(Modifier.padding(innerPadding))
        } else {
            if(timers.isEmpty()) {
                NoTimers(Modifier.padding(innerPadding)) { showBottomSheet = true }
            } else {
                TimersList(Modifier.padding(innerPadding).padding(horizontal = 16.dp), timers)
            }
        }
    }

    CreateTimerSheet(
        sheetState = sheetState,
        showBottomSheet = showBottomSheet,
        onDismissRequest = { showBottomSheet = false },
        onFinish = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    showBottomSheet = false
                    homeViewModel.updateTimers()
                }
            }
        }
    )
}

suspend fun showSnackbar(event: HomeEvent, snackbarHostState: SnackbarHostState, onUndoDelete: (TimerModel) -> Unit) {
    when(event.type) {
        HomeEventType.UNPIN -> {
            snackbarHostState.showSnackbar("Unpinned timer '${event.affectedTimer.name}'")
        }
        HomeEventType.PIN -> {
            snackbarHostState.showSnackbar("Pinned timer '${event.affectedTimer.name}'")
        }
        HomeEventType.DELETE -> {
            val result = snackbarHostState.showSnackbar(
                "Deleted timer '${event.affectedTimer.name}'",
                "Undo"
            )

            if(result == SnackbarResult.ActionPerformed) {
                onUndoDelete(event.affectedTimer)
            }
        }
        HomeEventType.DELETE_UNDONE -> {
            snackbarHostState.showSnackbar("Undid deletion of timer '${event.affectedTimer.name}'")
        }
        HomeEventType.CREATE -> {

        }
    }
}

@Composable
fun TimersList(modifier: Modifier, timers: List<TimerModel>) {
    LazyColumn(modifier) {
        items(timers) { timer ->
            TimerCard(Modifier.padding(bottom = 8.dp), timer)
        }
    }
}

@Composable
fun LoadingTimers(modifier: Modifier) {
    LinearProgressIndicator(modifier.fillMaxWidth())
}

@Composable
fun NoTimers(modifier: Modifier, openCreateSheet: () -> Unit) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            modifier = Modifier.size(64.dp),
            painter = painterResource(R.drawable.baseline_search_off_24),
            contentDescription = "No results"
        )

        Spacer(Modifier.height(12.dp))
        Text("No timers found", style = MaterialTheme.typography.titleLarge)
        Text("Create your first with the button below")

        Spacer(Modifier.height(12.dp))
        CreateTimerButton(openCreateSheet)
    }
}

@Composable
fun CreateTimerButton(openCreateSheet: () -> Unit) {
    Button(
        onClick = openCreateSheet,
    ) {
        Text("Create timer")
    }
}

@Composable
fun CreateTimerFAB(openCreateSheet: () -> Unit) {
    ExtendedFloatingActionButton(
        text = { Text("Create") },
        icon = { Icon(painterResource(R.drawable.outline_timer_24), "Timer") },
        onClick = openCreateSheet
    )
}