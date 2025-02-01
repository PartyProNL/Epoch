package me.partypronl.epoch.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomePage() {
    Scaffold { innerPadding ->
        Text(modifier = Modifier.padding(innerPadding), text = "Home")
    }
}