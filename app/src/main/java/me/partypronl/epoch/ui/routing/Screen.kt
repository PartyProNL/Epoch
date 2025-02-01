package me.partypronl.epoch.ui.routing

sealed class Screen(val route: String) {
    object Home: Screen("home")
}