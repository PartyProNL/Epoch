package me.partypronl.epoch.ui.routing

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import me.partypronl.epoch.ui.screens.HomePage

@Composable
fun NavigationGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomePage(navController)
        }
    }
}