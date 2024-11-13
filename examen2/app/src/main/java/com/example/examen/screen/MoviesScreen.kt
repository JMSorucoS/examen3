package com.example.examen.screen


import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.examen.Movie


@Composable
fun MoviesScreen(onClick: (Movie)->Unit) {
    Scaffold(
        content = {
                paddingValues -> MoviesScreenContent(
            modifier = Modifier.padding(paddingValues),
                    onClick=onClick, movieViewModel=movieViewModel)
        }
    )
}


@Composable
fun MoviesScreenContent(modifier: Modifier, onClick: (Movie)->Unit, movieViewModel:MovieViewModel) {
    Log.d("MOVIESCREEN", "MoviesScreenContent")
    var listOfMovies by remember { mutableStateOf(listOf<Movie>()) }
    val context = LocalContext.current
    val movieState by movieViewModel.state.collectAsStateWithLifcycle()
    when(movieState){
        is MovieViewModel.MovieState.Loading->{
            Column(
                verticalArrangement=Arrangement.Center,
                horizontalAlignment=Alignment.CenterHorizontally
            ){
                CircularProgressIndicator()
            }
        }
        is MovieViewModel.MovieState.Error->{
            Toast.makeText(context, "Error ${(movieState as MovieViewModel.MovieState.Error).errorMessage}", Toast.LENGTH_SHORT).show()
        }
    is MovieViewModel.MovieState.Successful -> {
        listOfMovies = (movieState as MovieViewModel.MovieState.Successful).list
    }
}


Column(
modifier = modifier.fillMaxSize()
) {
    Text( text = "Peliculas Populares")
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier) {
        items(listOfMovies.size) {
            ElevatedCard(
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                onClick = {
                    onClick(listOfMovies[it].id.toString())
                }
            ) {
                Text(
                    text = "${listOfMovies[it].title}",
                    modifier = Modifier
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
}
