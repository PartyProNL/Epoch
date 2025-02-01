package me.partypronl.epoch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import me.partypronl.epoch.data.DatabaseManager
import me.partypronl.epoch.ui.routing.NavigationGraph
import me.partypronl.epoch.ui.theme.EpochTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        DatabaseManager(this.applicationContext)

        enableEdgeToEdge()
        setContent {
            EpochTheme {
                val navController = rememberNavController()
                NavigationGraph(navController)
            }
        }
    }
}