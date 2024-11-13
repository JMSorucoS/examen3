package com.example.examen.navigation

sealed class Screens(val route: String) {
    object MoviesScreen: Screens("movies")
    object MovieDetailScreen: Screens("moviedetail")
}