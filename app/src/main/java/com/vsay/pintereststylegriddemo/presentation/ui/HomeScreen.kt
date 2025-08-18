package com.vsay.pintereststylegriddemo.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.vsay.pintereststylegriddemo.domain.model.Image
import com.vsay.pintereststylegriddemo.presentation.viewmodel.HomeViewModel

/**
 * Composable function for the Home Screen.
 *
 * This function displays a Pinterest-style grid of images using [LazyVerticalStaggeredGrid].
 * It collects a [LazyPagingItems] flow of images from the [viewModel] and handles different load states:
 * - Displays image items using [ImageCard].
 * - Shows a shimmer placeholder for items that are not yet loaded.
 * - Shows a full-screen loading indicator ([LoadingScreen]) during initial refresh.
 * - Shows an error message and a retry button ([ErrorScreen]) if the refresh fails.
 * - Shows a [CircularProgressIndicator] at the bottom when appending more items.
 *
 * The screen uses a [Scaffold] to provide a [TopAppBar] with the title "Pinterest Style Grid Demo".
 *
 * @param viewModel The [HomeViewModel] instance that provides the [LazyPagingItems] flow of images.
 */
@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val images = viewModel.pagingFlow.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pinterest Style Grid Demo") },
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Adaptive(minSize = 150.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(images.itemCount) { index ->
                    val item = images[index]
                    if (item != null) {
                        ImageCard(item)
                    } else {
                        // Shimmer for null placeholder
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp) // Keep a fixed height or make it dynamic if possible
                                .placeholder(
                                    visible = true,
                                    highlight = PlaceholderHighlight.shimmer(), // Consistent and clearer call
                                    color = Color.LightGray
                                )
                        )
                    }
                }
            }

            when {
                images.loadState.refresh is LoadState.Loading -> {
                    LoadingScreen()
                }

                images.loadState.refresh is LoadState.Error -> {
                    ErrorScreen(images.loadState.refresh, images)
                }

                images.loadState.append is LoadState.Loading -> {
                    LoadingScreen()
                }
            }
        }
    }
}

/**
 * Displays an error message and a retry button.
 * This composable is shown when the initial loading of images fails.
 *
 * @param refresh The current refresh [LoadState]. It's expected to be [LoadState.Error] in this context.
 * @param images The [LazyPagingItems] instance used to trigger a retry action.
 */
@Composable
private fun ErrorScreen(
    refresh: LoadState,
    images: LazyPagingItems<Image>
) {
    val error = (refresh as LoadState.Error).error
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Oops! Something went wrong:\n${error.localizedMessage}")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { images.retry() }) {
            Text("Retry")
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

/**
 * A Composable function that displays an image in a Card view.
 *
 * This function uses [SubcomposeAsyncImage] from Coil to load and display the image asynchronously,
 * allowing for a composable loading state.
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
        SubcomposeAsyncImage( // Changed from AsyncImage
            model = image.downloadURL,
            contentDescription = image.author,
            modifier = Modifier
                .fillMaxWidth()
                .height(image.height.dp) // dynamic height
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            loading = { // Composable content for loading state
                Box(
                    modifier = Modifier
                        .fillMaxSize() // Fill the space of SubcomposeAsyncImage
                        .placeholder(
                            visible = true,
                            highlight = PlaceholderHighlight.shimmer(),
                            color = Color.LightGray
                        )
                )
            }
        )
    }
}
