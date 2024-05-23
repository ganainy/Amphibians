package com.example.amphibians.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibians.R
import com.example.marsphotos.model.Amphibian


@Composable
fun HomeScreen(contentPadding: PaddingValues, amphibianUiState: AmphibianUiState,modifier: Modifier = Modifier) {
    when (amphibianUiState) {
        is AmphibianUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is AmphibianUiState.Success -> LazyGridLayoutExample(
            amphibianUiState.amphibians, modifier = modifier.fillMaxWidth().padding( top = contentPadding.calculateTopPadding())
        )
        is AmphibianUiState.Error -> ErrorScreen( modifier = modifier.fillMaxSize())
    }
}


@Composable
fun LazyGridLayoutExample(amphibians: List<Amphibian>, modifier: Modifier) {
    // Define the number of columns in the grid
    val columns = GridCells.Fixed(1)


    LazyVerticalGrid(
        columns = columns,
        modifier = modifier
    ) {
        // Add items to the grid
        items(amphibians) { amphibian ->
            AmphibiansCard(amphibian)
    }
}
}
/**
 * The home screen displaying the loading message.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.loading_img),
        contentDescription = stringResource(R.string.loading)
    )
}

/**
 * The home screen displaying error message with re-attempt button.
 */
@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error), contentDescription = ""
        )
        Text(text = stringResource(R.string.loading_failed), modifier = Modifier.padding(16.dp))
    }
}

@Composable
fun AmphibiansCard(amphibian: Amphibian, modifier: Modifier = Modifier) {
    Card(modifier = Modifier.padding(16.dp)) {
        Column {
            Text(
                text = "${amphibian.name} (${amphibian.type})", modifier = Modifier.padding(16.dp),
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 20.sp,
                    color = Color.Black
                )
            )
            //replace with coil
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current).data(amphibian.imgSrc)
                    .crossfade(true).build(),
                error = painterResource(R.drawable.ic_broken_image),
                placeholder = painterResource(R.drawable.loading_img),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = amphibian.description,
                modifier = Modifier.padding(16.dp),
                style = TextStyle(
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 18.sp,
                    color = Color.Gray
                )
            )
        }
    }
}