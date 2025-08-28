package com.vsay.pintereststylegriddemo.presentation.bookmark.ui // Or your preferred package structure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.vsay.pintereststylegriddemo.R
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
import com.vsay.pintereststylegriddemo.presentation.common.TopAppBarConfig
import com.vsay.pintereststylegriddemo.ui.common.NavigationIconType
import com.vsay.pintereststylegriddemo.ui.theme.PinterestStyleGridDemoTheme

@OptIn(ExperimentalMaterial3Api::class) // For TopAppBar
@Composable
fun BookmarkScreen(
    navController: NavController,
    appViewModel: AppViewModel,
) {

    val context = LocalContext.current // Get context for string resources

    LaunchedEffect(Unit) {
        appViewModel.showTopAppBar(
            TopAppBarConfig(
                title = context.getString(R.string.bottom_nav_bookmark),
                navigationIconType = NavigationIconType.BACK,
                onNavigationIconClick = {
                    navController.navigateUp()
                }
            )
        )
    }

    BookmarkScreenUI(modifier = Modifier)
}

@Composable
fun BookmarkScreenUI(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(), // Adjust padding if used within a Scaffold that provides its own
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bookmark Screen",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Your saved items will appear here.",
            style = MaterialTheme.typography.bodyLarge
        )
        // Add more UI elements like a LazyColumn for bookmarked items
    }
}

@Preview(showBackground = true)
@Composable
fun BookmarkScreenUIPreview() {
    PinterestStyleGridDemoTheme {
        BookmarkScreenUI(modifier = Modifier)
    }
}

