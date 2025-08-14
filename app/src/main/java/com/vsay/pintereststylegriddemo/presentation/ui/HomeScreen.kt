package com.vsay.pintereststylegriddemo.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vsay.pintereststylegriddemo.domain.model.Image
import com.vsay.pintereststylegriddemo.presentation.viewmodel.HomeViewModel

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val imagesState = viewModel.images.collectAsState()
    HomeScreenContent(images = imagesState.value)
}

@Composable
fun HomeScreenContent(images: List<Image>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pinterest Style Grid Demo") },
            )
        }
    ) { innerPadding ->
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding) // Apply padding from Scaffold
        ) {
            items(images) { image ->
                ImageCard(image)
            }
        }
    }
}

@Composable
fun ImageCard(image: Image) {
    Card(
        elevation = 4.dp,
        modifier = Modifier.padding(4.dp)
    ) {
        AsyncImage(
            model = image.imageUrl,
            contentDescription = image.description
        )
    }
}

// Previews

@Preview(name = "HomeScreen Light", showBackground = true)
@Composable
fun HomeScreenContentPreviewLight() {
    val fakeImages = listOf(
        Image(
            id = "1",
            imageUrl = "https://picsum.photos/seed/image1/200/300",
            description = "Image 1 Description"
        ),
        Image(
            id = "2",
            imageUrl = "https://picsum.photos/seed/image2/200/250",
            description = "Image 2 Description"
        ),
        Image(
            id = "3",
            imageUrl = "https://picsum.photos/seed/image3/200/350",
            description = "Image 3 Description"
        )
    )
    // Consider wrapping with your AppTheme if you have one, e.g., PinterestStyleGridDemoTheme { HomeScreenContent(images = fakeImages) }
    HomeScreenContent(images = fakeImages)
}

@Preview(name = "HomeScreen Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenContentPreviewDark() {
    val fakeImages = listOf(
        Image(
            id = "1",
            imageUrl = "https://picsum.photos/seed/image1/200/300",
            description = "Image 1 Description"
        ),
        Image(
            id = "2",
            imageUrl = "https://picsum.photos/seed/image2/200/250",
            description = "Image 2 Description"
        ),
        Image(
            id = "3",
            imageUrl = "https://picsum.photos/seed/image3/200/350",
            description = "Image 3 Description"
        )
    )
    // Consider wrapping with your AppTheme if you have one
    HomeScreenContent(images = fakeImages)
}

@Preview(name = "ImageCard Light", showBackground = true)
@Composable
fun ImageCardPreviewLight() {
    val sampleImage = Image(
        id = "sample",
        imageUrl = "https://picsum.photos/seed/sample1/200/300",
        description = "A sample image for card preview"
    )
    // Consider wrapping with your AppTheme if you have one
    ImageCard(image = sampleImage)
}

@Preview(name = "ImageCard Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ImageCardPreviewDark() {
    val sampleImage = Image(
        id = "sample",
        imageUrl = "https://picsum.photos/seed/sample2/200/300",
        description = "A sample image for card preview"
    )
    // Consider wrapping with your AppTheme if you have one
    ImageCard(image = sampleImage)
}
