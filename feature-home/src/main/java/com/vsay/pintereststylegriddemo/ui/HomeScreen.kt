package com.vsay.pintereststylegriddemo.ui

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.vsay.pintereststylegriddemo.common.NavigationIconType
import com.vsay.pintereststylegriddemo.common.TopAppBarConfig
import com.vsay.pintereststylegriddemo.core.domain.model.Image
import com.vsay.pintereststylegriddemo.core.navigation.navigators.IDetailNavigator
import com.vsay.pintereststylegriddemo.core.platform_utils.notification.NotificationHelper
import com.vsay.pintereststylegriddemo.core.ui.R
import com.vsay.pintereststylegriddemo.permission.rememberPermissionHandler
import com.vsay.pintereststylegriddemo.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

private const val TAG = "HomeScreen"

@SuppressLint("InlinedApi") //This annotation only suppresses the Lint warning.
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    detailNavigator: IDetailNavigator,
    homeViewModel: HomeViewModel = hiltViewModel(),
    onShowTopAppBar: (TopAppBarConfig) -> Unit,
    smallIconResId: Int,
) {
    val images = homeViewModel.pagingFlow.collectAsLazyPagingItems()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    var imagePendingNotification by remember { mutableStateOf<Image?>(null) }

    val notificationPermissionHandler = rememberPermissionHandler(
        permission = Manifest.permission.POST_NOTIFICATIONS,
        onPermissionGranted = {
            imagePendingNotification?.let { image ->
                val title = context.getString(
                    R.string.notification_title_image_from,
                    image.author
                )
                val text = context.getString(R.string.notification_text_tap_to_view, image.id)
                NotificationHelper.showDeepLinkNotification(
                    context,
                    image.id,
                    title,
                    text,
                    smallIconResId
                ) // Assuming ic_launcher is accessible here or passed
                imagePendingNotification = null // Clear after handling
            }
        },
        onPermissionDenied = { openAppSettings ->
            Log.w(TAG, "POST_NOTIFICATIONS permission denied by user.")
            imagePendingNotification = null // Clear if permission is denied
            scope.launch {
                val result = snackbarHostState.showSnackbar(
                    message = context.getString(R.string.notification_permission_rationale),
                    actionLabel = context.getString(R.string.button_settings),
                    duration = SnackbarDuration.Long
                )
                if (result == SnackbarResult.ActionPerformed) {
                    openAppSettings()
                }
            }
        },
        requiresPermission = Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU // Only required for Android 13+
    )

    LaunchedEffect(Unit) {
        Log.d(TAG, "Setting TopAppBar config for HomeScreen")
        // Use the onShowTopAppBar callback
        onShowTopAppBar(
            TopAppBarConfig(
                title = context.getString(R.string.home_screen_title),
                navigationIconType = NavigationIconType.MENU,
                onNavigationIconClick = {
                    Log.d(TAG, "Menu icon clicked on HomeScreen")
                    // TODO: Implement actual menu opening logic (e.g., via a callback)
                },
                actions = {
                    IconButton(onClick = {
                        Log.d(TAG, "Search icon clicked on HomeScreen")
                        // TODO: Implement search action (e.g., navigate to search screen)
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = context.getString(R.string.content_description_search)
                        )
                    }
                }
            )
        )
        // Channel creation should ideally be done once at app startup (e.g., in Application class)
        // NotificationHelper.createNotificationChannel(context) // Removed from here
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
                    ImageCardM3(
                        image = item,
                        onClick = {
                            detailNavigator.navigateToDetail(item.id)
                        },
                        onLongClick = {
                            Log.d(TAG, "Long press on image: ${item.id}")
                            if (notificationPermissionHandler.hasPermission.value) {
                                val title = context.getString(
                                    R.string.notification_title_image_from,
                                    item.author
                                )
                                val text = context.getString(
                                    R.string.notification_text_tap_to_view,
                                    item.id
                                )
                                // Ensure R.mipmap.ic_launcher is appropriate here or pass it from a suitable source
                                NotificationHelper.showDeepLinkNotification(
                                    context,
                                    item.id,
                                    title,
                                    text,
                                    smallIconResId
                                )
                            } else {
                                imagePendingNotification = item
                                notificationPermissionHandler.requestPermission()
                            }
                        }
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(250.dp)
                            .placeholder(
                                visible = true,
                                highlight = PlaceholderHighlight.shimmer(),
                                color = Color.LightGray
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
                    text = context.getString(
                        R.string.error_message_generic,
                        error.localizedMessage ?: context.getString(R.string.unknown_error)
                    ),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.error
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { images.retry() }) {
                    Text(context.getString(R.string.button_retry))
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

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageCardM3(
    image: Image,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = modifier
            .padding(4.dp)
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
    ) {
        SubcomposeAsyncImage(
            model = image.downloadURL,
            contentDescription = image.author,
            modifier = Modifier
                .fillMaxWidth()
                // .height(image.height.dp) // Using a fixed or aspect ratio height might be better
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
