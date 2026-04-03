package com.pipeytube.navigation

sealed class AppDestination(val route: String, val title: String) {
    data object Search : AppDestination("search", "Search")
    data object Player : AppDestination("player", "Player")
    data object Details : AppDestination("details", "Details")
}
