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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import me.partypronl.epoch.data.models.TimerModel

@Composable
fun TimerCard(modifier: Modifier, timer: TimerModel) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.large)
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
                    0.75F
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}