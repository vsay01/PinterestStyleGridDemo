package com.vsay.pintereststylegriddemo.feature_detail.ui

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
import com.vsay.pintereststylegriddemo.common.NavigationIconType
import com.vsay.pintereststylegriddemo.common.TopAppBarConfig
import com.vsay.pintereststylegriddemo.core.domain.model.Image
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes
import com.vsay.pintereststylegriddemo.core.ui.R
import com.vsay.pintereststylegriddemo.feature_detail.viewmodel.DetailViewModel
import com.vsay.pintereststylegriddemo.feature_detail.viewmodel.UiState
import com.vsay.pintereststylegriddemo.theme.PinterestStyleGridDemoTheme

private const val TAG = "DetailScreen"

@Composable
fun DetailScreen(
    navController: NavController, // For back navigation
    onShowTopAppBar: (TopAppBarConfig) -> Unit, // Callback for TopAppBar
    viewModel: DetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current // Get context for string resources

    LaunchedEffect(uiState, navController) { // Key on uiState to update title
        val currentImage = (uiState as? UiState.Success<Image>)?.data
        Log.d(TAG, "Updating TopAppBar for DetailScreen. Image author: ${currentImage?.author}")
        // appViewModel.showTopAppBar( // Replaced
        onShowTopAppBar(
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
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is UiState.Success -> {
                DetailScreenUI(
                    image = state.data,
                    onNavigateToProfile = {
                        navController.navigate(AppRoutes.Profile.ProfileRoot.route)
                    }
                )
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
                    if (state.message != specificErrorMessage) {
                        Button(onClick = { viewModel.retry() }) {
                            Text(stringResource(id = R.string.button_retry))
                        }
                    }
                }
            }
        }
    }
}

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
            )
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
                .data(image.downloadURL)
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
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
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

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onNavigateToProfile,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Profile Section")
        }
    }
}

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
        Text(
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
                text = context.getString(R.string.error_invalid_or_missing_image_id),
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
    }
}
