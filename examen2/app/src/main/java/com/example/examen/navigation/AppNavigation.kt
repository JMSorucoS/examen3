package com.example.examen.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.examen.screen.MovieDetailScreen
import com.example.examen.screen.MoviesScreen
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.examen.viewmodel.MovieViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screens.MoviesScreen.route
    ) {
        composable(Screens.MoviesScreen.route) {
            val movieViewModel : MovieViewModel = hiltViewModel()
            MoviesScreen(
                onClick = {
                    movie ->navController.navigate("${Screens.MovieDetailScreen.route}/${Json.encodeToString(movie)}")
                },
                movieViewModel
            )
        }
        composable(
            route = "${Screens.MovieDetailScreen.route}/{movieId}",
            arguments = listOf(
                navArgument("movieId") {
                    type = NavType.StringType
                }
            )
        ) {
            MovieDetailScreen(
                onBackPressed = {
                    navController.popBackStack()
                },
                movieId = it.arguments?.getString("movieId")?:""
                //movie = Json.decodeFromString<Movie>(it.arguments?.getString("movie")?:"")
            )
        }
    }
}
