package me.partypronl.epoch.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import me.partypronl.epoch.data.models.TimerModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf

@Composable
fun TimerCard(modifier: Modifier, timer: TimerModel) {
    var progress by remember { mutableFloatStateOf(timer.getProgress()) }

    LaunchedEffect(timer) {
        while (true) {
            progress = timer.getProgress()
            delay(1000L)
        }
    }

    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest, MaterialTheme.shapes.large)
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Text(
            text = timer.name,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )

        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            CircularProgressIndicator(
                progress = {
                    progress
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}