package com.vsay.pintereststylegriddemo.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vsay.pintereststylegriddemo.domain.model.Image
import com.vsay.pintereststylegriddemo.presentation.viewmodel.HomeViewModel
import com.vsay.pintereststylegriddemo.presentation.viewmodel.UiState

/**
 * Composable function for the Home Screen.
 *
 * This function observes the UI state from the [viewModel] and displays different content
 * based on the current state. It uses a [Scaffold] to provide a consistent [TopAppBar].
 * - [UiState.Loading]: Shows a centered [CircularProgressIndicator].
 * - [UiState.Success]: Shows the [HomeScreenContent] with the list of images.
 * - [UiState.Error]: Shows an error message.
 *
 * @param viewModel The [HomeViewModel] instance that provides the UI state.
 */
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val uiState = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pinterest Style Grid Demo") },
            )
        }
    ) { innerPadding ->
        when (val state = uiState.value) {
            is UiState.Loading -> LoadingScreen(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )

            is UiState.Success -> HomeScreenContent(
                images = state.data,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )

            is UiState.Error -> ErrorScreen(
                message = state.message,
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }
    }
}

/**
 * Displays a loading indicator centered on a white background.
 * @param modifier The modifier to be applied to this layout.
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(Color.White), // Set background to white
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

/**
 * Displays an error message centered on the screen.
 * @param message The error message to display.
 * @param modifier The modifier to be applied to this layout.
 */
@Composable
fun ErrorScreen(message: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = "Error: $message"
        ) // Consider a more user-friendly error display
    }
}

/**
 * Displays the main content of the home screen, which is a Pinterest-style grid of images.
 *
 * @param images The list of [Image] objects to be displayed in the grid.
 * @param modifier The modifier to be applied to this layout, including padding from the Scaffold.
 */
@Composable
fun HomeScreenContent(images: List<Image>, modifier: Modifier = Modifier) {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        modifier = modifier
    ) {
        items(images) { image ->
            ImageCard(image)
        }
    }
}

/**
 * A Composable function that displays an image in a Card view.
 *
 * This function uses [AsyncImage] from Coil to load and display the image asynchronously.
 * The image height is dynamic, based on the `height` property of the [Image] object.
 *
 * @param image The [Image] object containing the image URL, author, and dimensions.
 */
@Composable
fun ImageCard(image: Image) {
    Card(
        elevation = 4.dp,
        modifier = Modifier.padding(4.dp)
    ) {
        AsyncImage(
            model = image.downloadURL,
            contentDescription = image.author,
            modifier = Modifier
                .fillMaxWidth()
                .height(image.height.dp) // dynamic height
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

// Previews

@Preview(name = "LoadingScreenPreview", showBackground = true)
@Composable
fun LoadingScreenPreview() {
    Scaffold(topBar = { TopAppBar(title = { Text("Preview") }) }) { padding ->
        LoadingScreen(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        )
    }
}

@Preview(name = "ErrorScreenPreview", showBackground = true)
@Composable
fun ErrorScreenPreview() {
    Scaffold(topBar = { TopAppBar(title = { Text("Preview") }) }) { padding ->
        ErrorScreen(
            message = "Something went wrong!",
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        )
    }
}

@Preview(name = "HomeScreen Light", showBackground = true)
@Composable
fun HomeScreenContentPreviewLight() {
    val fakeImages = listOf(
        Image(
            id = "1",
            author = "author 1",
            url = "https://picsum.photos/seed/image1/200/300",
            width = 200,
            height = 300,
            downloadURL = "https://picsum.photos/id/237/200/300"
        ),
        Image(
            id = "2",
            url = "https://picsum.photos/seed/image2/200/250",
            width = 200,
            height = 300,
            downloadURL = "https://picsum.photos/id/237/200/300",
            author = "author 2"
        ),
        Image(
            id = "3",
            url = "https://picsum.photos/seed/image3/200/350",
            width = 200,
            height = 300,
            downloadURL = "https://picsum.photos/id/237/200/300",
            author = "author 3"
        )
    )
    Scaffold(topBar = { TopAppBar(title = { Text("Preview") }) }) { padding ->
        HomeScreenContent(
            images = fakeImages, modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        )
    }
}

@Preview(name = "HomeScreen Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenContentPreviewDark() {
    val fakeImages = listOf(
        Image(
            id = "1",
            author = "author 1",
            url = "https://picsum.photos/seed/image1/200/300",
            width = 200,
            height = 300,
            downloadURL = "https://picsum.photos/id/237/200/300"
        ),
        Image(
            id = "2",
            url = "https://picsum.photos/seed/image2/200/250",
            width = 200,
            height = 300,
            downloadURL = "https://picsum.photos/id/237/200/300",
            author = "author 2"
        ),
        Image(
            id = "3",
            url = "https://picsum.photos/seed/image3/200/350",
            width = 200,
            height = 300,
            downloadURL = "https://picsum.photos/id/237/200/300",
            author = "author 3"
        )
    )
    Scaffold(topBar = { TopAppBar(title = { Text("Preview") }) }) { padding ->
        HomeScreenContent(
            images = fakeImages, modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        )
    }
}

@Preview(name = "ImageCard Light", showBackground = true)
@Composable
fun ImageCardPreviewLight() {
    val sampleImage = Image(
        id = "1",
        author = "author 1",
        url = "https://picsum.photos/seed/image1/200/300",
        width = 200,
        height = 300,
        downloadURL = "https://picsum.photos/id/237/200/300"
    )
    ImageCard(image = sampleImage)
}

@Preview(name = "ImageCard Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImageCardPreviewDark() {
    val sampleImage = Image(
        id = "1",
        author = "author 1",
        url = "https://picsum.photos/seed/image1/200/300",
        width = 200,
        height = 300,
        downloadURL = "https://picsum.photos/id/237/200/300"
    )
    ImageCard(image = sampleImage)
}

