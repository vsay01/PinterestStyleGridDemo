package com.vsay.pintereststylegriddemo.presentation.detail.ui

// Material 3 imports
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.vsay.pintereststylegriddemo.R
import com.vsay.pintereststylegriddemo.common.navigation.AppRoute
import com.vsay.pintereststylegriddemo.domain.model.Image
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
import com.vsay.pintereststylegriddemo.presentation.common.TopAppBarConfig
import com.vsay.pintereststylegriddemo.presentation.detail.viewmodel.DetailViewModel
import com.vsay.pintereststylegriddemo.presentation.detail.viewmodel.UiState
import com.vsay.pintereststylegriddemo.ui.common.NavigationIconType
import com.vsay.pintereststylegriddemo.ui.theme.PinterestStyleGridDemoTheme

private const val TAG = "DetailScreen"

/**
 * Composable function for the Detail Screen.
 *
 * This screen displays the details of a selected image. It handles different UI states:
 * Loading, Success (displaying image details), and Error.
 * It also updates the TopAppBar with the image author's name and a back navigation icon.
 *
 * @param appViewModel The [AppViewModel] used to control the TopAppBar.
 * @param navController The [NavController] used for navigating back from this screen.
 * @param viewModel The [DetailViewModel] responsible for fetching and managing the image details.
 *                  It is provided by Hilt.
 */
@Composable
fun DetailScreen(
    appViewModel: AppViewModel,
    navController: NavController, // For back navigation
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current // Get context for string resources

    LaunchedEffect(uiState, navController) { // Key on uiState to update title
        val currentImage = (uiState as? UiState.Success<Image>)?.data
        Log.d(TAG, "Updating TopAppBar for DetailScreen. Image author: ${currentImage?.author}")
        appViewModel.showTopAppBar(
            TopAppBarConfig(
                title = currentImage?.author ?: context.getString(R.string.details_screen_title),
                navigationIconType = NavigationIconType.BACK,
                onNavigationIconClick = {
                    Log.d(TAG, "Back button clicked on DetailScreen")
                    navController.navigateUp()
                }
                // actions = { /* Add detail specific actions if any */ }
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        when (val state = uiState) {
            is UiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center)) // M3 CircularProgressIndicator
            }

            is UiState.Success -> {
                DetailScreenUI(
                    image = state.data,
                    onNavigateToProfile = {
                        navController.navigate(AppRoute.Profile.ProfileRoot.route)
                    }
                ) // state.data is Image?
            }

            is UiState.Error -> {
                val specificErrorMessage =
                    stringResource(id = R.string.error_invalid_or_missing_image_id)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = state.message,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    // Only show Retry button if the error message is NOT the specific one
                    if (state.message != specificErrorMessage) {
                        Button(onClick = { viewModel.retry() }) { // M3 Button
                            Text(stringResource(id = R.string.button_retry))
                        }
                    }
                }
            }
        }
    }
}

/**
 * Composable function that displays the UI for the detail screen when image data is successfully loaded.
 * It shows the image itself and its attributes like ID, author, dimensions, and a clickable download URL.
 * If the image data is null, it displays a message indicating that the data is not available.
 *
 * @param image The [Image] object containing the details to display. Can be null.
 * @param modifier The [Modifier] to be applied to the root Column of this composable.
 */
@Composable
fun DetailScreenUI(
    image: Image?,
    modifier: Modifier = Modifier,
    onNavigateToProfile: () -> Unit
) {
    if (image == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                stringResource(id = R.string.image_data_not_available),
                style = MaterialTheme.typography.bodyLarge
            ) // M3 Text
        }
        return
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        var isImageLoading by remember { mutableStateOf(true) }

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(image.downloadURL) // Ensure this is the direct displayable URL
                .crossfade(true)
                .build(),
            contentDescription = stringResource(
                id = R.string.content_description_image_by_author,
                image.author ?: stringResource(id = R.string.unknown_author)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(image.width.toFloat() / image.height.toFloat().coerceAtLeast(1f))
                .placeholder(
                    visible = isImageLoading,
                    highlight = PlaceholderHighlight.shimmer(),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f) // M3 placeholder color
                ),
            contentScale = ContentScale.Crop,
            onState = { state ->
                isImageLoading = state is AsyncImagePainter.State.Loading ||
                        state is AsyncImagePainter.State.Error
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        DetailAttributeRow(label = stringResource(id = R.string.label_id), value = image.id)
        DetailAttributeRow(
            label = stringResource(id = R.string.label_author),
            value = image.author ?: stringResource(id = R.string.unknown_author)
        )
        DetailAttributeRow(
            label = stringResource(id = R.string.label_width),
            value = stringResource(id = R.string.label_pixels_value, image.width)
        )
        DetailAttributeRow(
            label = stringResource(id = R.string.label_height),
            value = stringResource(id = R.string.label_pixels_value, image.height)
        )

        val uriHandler = LocalUriHandler.current
        DetailAttributeRow(
            label = stringResource(id = R.string.label_download_url),
            value = image.downloadURL,
            isClickableValue = true,
            onValueClick = {
                uriHandler.openUri(image.downloadURL)
            }
        )

        Spacer(modifier = Modifier.height(16.dp)) // Add some space before the new button

        // vvv NEW BUTTON FOR CROSS-GRAPH NAVIGATION vvv
        Button(
            onClick = onNavigateToProfile, // Use the new lambda
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Profile Section")
        }
        // ^^^ NEW BUTTON FOR CROSS-GRAPH NAVIGATION ^^^
    }
}

/**
 * A private composable function that displays a single attribute of the image in a row format.
 * It consists of a label and a value. The value can optionally be clickable.
 *
 * @param label The text label for the attribute (e.g., "Author", "Width").
 * @param value The actual value of the attribute.
 * @param isClickableValue A boolean indicating whether the `value` text should be styled
 *                         as a clickable link and respond to clicks. Defaults to `false`.
 * @param onValueClick A lambda function to be executed when the `value` is clicked.
 *                     This is only relevant if `isClickableValue` is `true`. Defaults to `null`.
 */
@Composable
private fun DetailAttributeRow(
    label: String,
    value: String,
    isClickableValue: Boolean = false,
    onValueClick: (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "$label:",
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.weight(0.3f)
        )
        val valueStyle = if (isClickableValue) {
            MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        } else {
            MaterialTheme.typography.bodyMedium
        }
        Text( // M3 Text
            text = value,
            style = valueStyle,
            modifier = Modifier
                .weight(0.7f)
                .then(if (onValueClick != null) Modifier.clickable(onClick = onValueClick) else Modifier)
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}


@Preview(showBackground = true, name = "Detail Screen UI - Loaded")
@Composable
fun DetailScreenUILoadedPreview() {
    PinterestStyleGridDemoTheme {
        DetailScreenUI(
            image = Image(
                id = "0",
                author = "Alejandro Escamilla",
                url = "https://picsum.photos/id/0/600/400",
                width = 600,
                height = 400,
                downloadURL = "https://picsum.photos/id/0/5000/3333"
            ),
            onNavigateToProfile = {}
        )
    }
}

@Preview(showBackground = true, name = "Detail Screen UI - Image Null")
@Composable
fun DetailScreenUIImageNullPreview() {
    PinterestStyleGridDemoTheme {
        DetailScreenUI(image = null, onNavigateToProfile = {})
    }
}

@Preview(showBackground = true, name = "Detail Screen - Loading State (Simulated)")
@Composable
fun DetailScreenLoadingStatePreview() {
    PinterestStyleGridDemoTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}

@Preview(showBackground = true, name = "Detail Screen - Error State (Simulated - General)")
@Composable
fun DetailScreenErrorStateGeneralPreview() {
    PinterestStyleGridDemoTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Sample error message.",
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Button(onClick = { /* No action in preview */ }) {
                Text("Retry")
            }
        }
    }
}

@Preview(showBackground = true, name = "Detail Screen - Error State (Invalid ID)")
@Composable
fun DetailScreenErrorStateInvalidIdPreview() {
    val context = LocalContext.current
    PinterestStyleGridDemoTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                // Simulating the specific error message from strings.xml
                text = context.getString(R.string.error_invalid_or_missing_image_id),
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            // Retry button should NOT appear here
        }
    }
}
