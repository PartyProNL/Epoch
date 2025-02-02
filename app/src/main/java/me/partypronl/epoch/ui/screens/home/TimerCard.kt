package me.partypronl.epoch.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import me.partypronl.epoch.data.models.TimerModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import me.partypronl.epoch.R
import me.partypronl.epoch.util.DateUtil
import me.partypronl.epoch.viewmodel.HomeViewModel

@Composable
fun TimerCard(modifier: Modifier, timer: TimerModel, homeViewModel: HomeViewModel = hiltViewModel()) {
    var progress by remember { mutableFloatStateOf(timer.getProgress()) }
    var timeLeft by remember { mutableLongStateOf(timer.ends - System.currentTimeMillis()) }

    LaunchedEffect(timer) {
        while (true) {
            progress = timer.getProgress()
            timeLeft = timer.ends - System.currentTimeMillis()
            if(timeLeft < 0) timeLeft = 0
            delay(1000L)
        }
    }

    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest, MaterialTheme.shapes.large)
            .padding(horizontal = 8.dp, vertical = 8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = timer.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(top = 8.dp, start = 8.dp)
            )

            Row {
                if(timer.pinned) {
                    IconButton(
                        onClick = { homeViewModel.setPinned(timer.id, false) }
                    ) {
                        Icon(painterResource(R.drawable.baseline_push_pin_24), "Pin")
                    }
                } else {
                    IconButton(
                        onClick = { homeViewModel.setPinned(timer.id, true) }
                    ) {
                        Icon(painterResource(R.drawable.outline_push_pin_24), "Pin")
                    }
                }
                
                IconButton(
                    onClick = {}
                ) {
                    Icon(Icons.Default.MoreVert, "Options")
                }
            }
        }


        Box(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = {
                    progress
                },
                modifier = Modifier.fillMaxSize().aspectRatio(1F),
                trackColor = MaterialTheme.colorScheme.primaryContainer
            )

            Text(
                text = if(timeLeft >= 1000) DateUtil.formatTime(timeLeft) else "Done",
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}