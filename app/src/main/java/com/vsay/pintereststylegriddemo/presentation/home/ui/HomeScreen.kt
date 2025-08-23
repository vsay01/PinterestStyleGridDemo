package com.vsay.pintereststylegriddemo.presentation.home.ui

// Material 3 Imports
import android.util.Log
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.vsay.pintereststylegriddemo.domain.model.Image
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
import com.vsay.pintereststylegriddemo.presentation.common.TopAppBarConfig
import com.vsay.pintereststylegriddemo.presentation.home.viewmodel.HomeViewModel
import com.vsay.pintereststylegriddemo.presentation.navigation.NavigationIconType

private const val TAG = "HomeScreen"

/**
 * Composable function for the Home Screen.
 *
 * This screen displays a Pinterest-style staggered grid of images fetched using pagination.
 * It handles loading states (initial load, error, loading more items) and provides
 * functionality for image clicks. It also configures the TopAppBar for this screen.
 *
 * @param appViewModel The [AppViewModel] used to control application-level UI states like the TopAppBar.
 * @param homeViewModel The [HomeViewModel] responsible for fetching and managing the image data for this screen.
 *                      Defaults to an instance provided by Hilt.
 * @param onImageClick A lambda function that is invoked when an image in the grid is clicked.
 *                     It receives the clicked [Image] object as a parameter.
 */
@Composable
fun HomeScreen(
    appViewModel: AppViewModel,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onImageClick: (image: Image) -> Unit
) {
    val images = homeViewModel.pagingFlow.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        Log.d(TAG, "Setting TopAppBar config for HomeScreen")
        appViewModel.showTopAppBar(
            TopAppBarConfig(
                title = "Home",
                navigationIconType = NavigationIconType.MENU,
                onNavigationIconClick = {
                    Log.d(TAG, "Menu icon clicked on HomeScreen")
                    // TODO: Implement actual menu opening logic
                },
                actions = {
                    IconButton(onClick = {
                        Log.d(TAG, "Search icon clicked on HomeScreen")
                        // TODO: Implement search action
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            )
        )
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
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
                    ImageCardM3(item, modifier = Modifier.clickable { onImageClick(item) })
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .placeholder(
                                visible = true,
                                highlight = PlaceholderHighlight.shimmer(),
                                color = Color.LightGray // Placeholder color
                            )
                    )
                }
            }
        }

        if (images.loadState.refresh is LoadState.Loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        if (images.loadState.refresh is LoadState.Error) {
            val error = (images.loadState.refresh as LoadState.Error).error
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Oops! Something went wrong:\n${error.localizedMessage}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { images.retry() }) {
                    Text("Retry")
                }
            }
        }

        if (images.loadState.append is LoadState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

/**
 * A Composable function that displays an image within a Material 3 Card.
 *
 * This function uses [SubcomposeAsyncImage] from Coil to load and display the image
 * asynchronously. It shows a placeholder with a shimmer effect while the image is loading.
 * The image is cropped to fit the bounds and has rounded corners.
 *
 * @param image The [Image] object containing the URL and dimensions for the image to be displayed.
 * @param modifier The [Modifier] to be applied to the Card. Defaults to [Modifier].
 */
@Composable
fun ImageCardM3(image: Image, modifier: Modifier = Modifier) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier.padding(4.dp)
    ) {
        SubcomposeAsyncImage(
            model = image.downloadURL,
            contentDescription = image.author,
            modifier = Modifier
                .fillMaxWidth()
                .height(image.height.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
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
