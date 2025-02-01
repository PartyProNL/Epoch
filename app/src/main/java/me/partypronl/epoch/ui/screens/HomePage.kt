package me.partypronl.epoch.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import me.partypronl.epoch.viewmodel.HomeViewModel
import me.partypronl.epoch.R

@Composable
fun HomePage(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val loadingTimers by homeViewModel.loadingTimers.collectAsState()
    val timers by homeViewModel.timers.collectAsState()

    Scaffold(
        floatingActionButton = { CreateTimerFAB() }
    ) { innerPadding ->
        if(loadingTimers) {
            LoadingTimers(Modifier.padding(innerPadding))
        } else {
            if(timers.isEmpty()) {
                NoTimers(Modifier.padding(innerPadding))
            } else {
//                ProjectsList(Modifier.padding(innerPadding).padding(horizontal = 16.dp), projects, navController)
            }
        }
    }
}

@Composable
fun LoadingTimers(modifier: Modifier) {
    LinearProgressIndicator(modifier.fillMaxWidth())
}

@Composable
fun NoTimers(modifier: Modifier) {
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
        CreateTimerButton()
    }
}

@Composable
fun CreateTimerButton() {
    Button(
        onClick = {},
    ) {
        Text("Create timer")
    }
}

@Composable
fun CreateTimerFAB() {
    ExtendedFloatingActionButton(
        text = { Text("Create") },
        icon = { Icon(painterResource(R.drawable.baseline_timer_24), "Timer") },
        onClick = {}
    )
}