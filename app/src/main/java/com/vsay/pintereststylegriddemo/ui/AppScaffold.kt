package com.vsay.pintereststylegriddemo.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
import com.vsay.pintereststylegriddemo.presentation.navigation.AppNavHost
import com.vsay.pintereststylegriddemo.presentation.navigation.NavigationIconType

/**
 * A Composable function that sets up the main application UI with a top app bar.
 *
 * This function uses a [Scaffold] to provide a basic structure with a [TopAppBar].
 * The configuration of the [TopAppBar] (visibility, title, navigation icon, actions)
 * is driven by the [AppViewModel.topAppBarConfig] state.
 *
 * The main content of the application is provided by [AppNavHost], which handles
 * navigation between different screens.
 *
 * @param appViewModel The [AppViewModel] instance that holds the application's UI state,
 *                     including the configuration for the top app bar.
 * @param navController The [NavHostController] used for navigation. Defaults to a
 *                     remembered NavController if not provided.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppWithTopBar(
    appViewModel: AppViewModel,
    navController: NavHostController = rememberNavController()
) {
    val topAppBarConfig by appViewModel.topAppBarConfig.collectAsState()

    Scaffold(
        topBar = {
            if (topAppBarConfig.isVisible) {
                TopAppBar(
                    title = {
                        topAppBarConfig.title?.let { Text(text = it) }
                    },
                    navigationIcon = {
                        topAppBarConfig.onNavigationIconClick?.let { onClick ->
                            val icon = when (topAppBarConfig.navigationIconType) {
                                NavigationIconType.BACK -> Icons.AutoMirrored.Filled.ArrowBack
                                NavigationIconType.MENU -> Icons.Filled.Menu
                                NavigationIconType.CLOSE -> Icons.Filled.Close
                                NavigationIconType.NONE -> null
                            }
                            val description = when (topAppBarConfig.navigationIconType) {
                                NavigationIconType.BACK -> "Back"
                                NavigationIconType.MENU -> "Menu"
                                NavigationIconType.CLOSE -> "Close"
                                NavigationIconType.NONE -> null
                            }
                            icon?.let { imageVector ->
                                IconButton(onClick = onClick) {
                                    Icon(imageVector, description)
                                }
                            }
                        }
                    },
                    actions = topAppBarConfig.actions
                )
            }
        }
    ) { innerPadding ->
        // Pass AppViewModel and NavController to AppNavHost
        // AppNavHost will then pass AppViewModel to individual screens
        AppNavHost(
            navController = navController,
            appViewModel = appViewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}
