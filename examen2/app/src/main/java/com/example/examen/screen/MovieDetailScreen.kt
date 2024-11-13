package com.example.examen.screen


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.examen.Movie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(onBackPressed: () ->Unit, movie: Movie) {
    Scaffold(
        topBar={
            TopAppBar(title = {
                    Text(text = "Movie Details")
                }, navigationIcon = {
                    IconButton(
                        onClick = onBackPressed,
                        content = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    )

                }
            )
               }, content = { paddingValues ->
                    MovieDetailScreenContent(
                        modifier = Modifier.padding(paddingValues), movie = movie
                    )
                })
}



@Composable
fun MovieDetailScreenContent(modifier: Modifier, movieId: String) {
    Log.d("MovieDetailScreenContent", "MovieDetailScreenContent UI")
    val viewModel = MovieDetailViewModel()


    var movieUI by remember { mutableStateOf(Movie(1, "", "", "")) }


    viewModel.findMovie(LocalContext.current, movieId)
    val context = LocalContext.current


    fun updateUI(movieDetailState: MovieDetailViewModel.MovieDetailState) {
        when ( movieDetailState) {
            is MovieDetailViewModel.MovieDetailState.Error -> {
                Toast.makeText(context, "Error ${movieDetailState.message}", Toast.LENGTH_SHORT).show()
            }
            is MovieDetailViewModel.MovieDetailState.Loading -> {
                Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
            }
            is MovieDetailViewModel.MovieDetailState.Successful -> {
                movieUI = movieDetailState.movie
            }
        }
    }
    viewModel.state.observe(
        LocalLifecycleOwner.current,
        Observer(::updateUI)
    )




    Column(
        modifier = modifier
    ) {
        Text(text = movieUI.title )
        Text(text = movieUI.description)
    }
}

