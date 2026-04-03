package com.pipeytube.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.pipeytube.player.PlayerRoute
import com.pipeytube.search.SearchRoute
import com.pipeytube.video.details.VideoDetailsRoute

@Composable
fun PipeytubeApp() {
    val navController = rememberNavController()
    val destinations = listOf(
        AppDestination.Search,
        AppDestination.Player,
        AppDestination.Details
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                destinations.forEach { destination ->
                    val selected = currentDestination?.hierarchy?.any { it.route == destination.route } == true
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(destination.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        label = { Text(destination.title) },
                        icon = { Text(destination.title.first().toString()) }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestination.Search.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(AppDestination.Search.route) { SearchRoute() }
            composable(AppDestination.Player.route) { PlayerRoute() }
            composable(AppDestination.Details.route) { VideoDetailsRoute() }
        }
    }
}
