package com.pipeytube.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.pipeytube.domain.repository.VideoRepository
import com.pipeytube.ui.player.PlayerScreen
import com.pipeytube.ui.search.SearchScreen

@Composable
fun PipeytubeApp(repository: VideoRepository) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "search") {
        composable("search") {
            SearchScreen(
                repository = repository,
                onVideoClick = { id -> navController.navigate("player/$id") }
            )
        }
        composable(
            route = "player/{videoId}",
            arguments = listOf(navArgument("videoId") { type = NavType.StringType })
        ) { entry ->
            PlayerScreen(
                videoId = entry.arguments?.getString("videoId").orEmpty(),
                repository = repository,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
