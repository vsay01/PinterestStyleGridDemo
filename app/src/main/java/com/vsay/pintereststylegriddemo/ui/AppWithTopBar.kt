package com.vsay.pintereststylegriddemo.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vsay.pintereststylegriddemo.R
import com.vsay.pintereststylegriddemo.common.navigation.AppNavHost
import com.vsay.pintereststylegriddemo.common.navigation.AppRoute
import com.vsay.pintereststylegriddemo.common.navigation.BottomNavItem
import com.vsay.pintereststylegriddemo.feature_bookmarks.ui.BOOKMARK_SCREEN_ROUTE
import com.vsay.pintereststylegriddemo.feature_bookmarks.ui.BOOKMARKS_GRAPH_ROUTE
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
import com.vsay.pintereststylegriddemo.presentation.common.TopAppBarConfig // Using your actual class
import com.vsay.pintereststylegriddemo.ui.common.NavigationIconType

/**
 * Composable function that sets up the main application UI, including the top app bar,
 * bottom navigation bar, and the main content area for navigation.
 *
 * It observes the current navigation route and updates the [TopAppBarConfig] in the [AppViewModel]
 * accordingly. This allows different screens to have customized top app bars (e.g., different titles,
 * navigation icons, visibility).
 *
 * The bottom navigation bar provides navigation between the main sections of the app: Home, Bookmark, and Profile.
 *
 * @param appViewModel The [AppViewModel] instance that holds the application's state,
 *                     including the [TopAppBarConfig] and handles top app bar updates.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppWithTopBar(
    appViewModel: AppViewModel,
) {
    val mainNavController = rememberNavController()
    val topAppBarConfigState by appViewModel.topAppBarConfig.collectAsState()
    val context = LocalContext.current

    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            BOOKMARK_SCREEN_ROUTE -> {
                appViewModel.updateTopAppBar(
                    TopAppBarConfig(
                        title = context.getString(com.vsay.pintereststylegriddemo.feature.bookmarks.R.string.bookmark_screen_title),
                        navigationIconType = NavigationIconType.BACK,
                        onNavigationIconClick = { mainNavController.navigateUp() },
                        isVisible = true
                        // actions = {} // Add if needed
                    )
                )
            }
            AppRoute.Main.Home.route -> {
                appViewModel.updateTopAppBar(
                    TopAppBarConfig(
                        title = context.getString(R.string.home_screen_title),
                        navigationIconType = NavigationIconType.NONE,
                        isVisible = true
                    )
                )
            }
            AppRoute.Main.Detail.route -> {
                appViewModel.updateTopAppBar(
                    TopAppBarConfig(
                        title = context.getString(R.string.details_screen_title),
                        navigationIconType = NavigationIconType.BACK,
                        onNavigationIconClick = { mainNavController.navigateUp() },
                        isVisible = true
                    )
                )
            }
            // Add other routes from AppRoute that need specific TopAppBar configurations

            else -> {
                // If the route is not handled explicitly, hide the app bar.
                // AppViewModel.hideTopAppBar() sets isVisible = false while keeping other config.
                // This is good if you want to animate out/in an app bar with the same title.
                // If a completely new config (or lack thereof) is desired, use updateTopAppBar.
                // For simplicity, hideTopAppBar() is fine here.
                appViewModel.hideTopAppBar()
            }
        }
    }

    val bottomNavItems = listOf(
        BottomNavItem.Home,
        BottomNavItem.Bookmark,
        BottomNavItem.Profile
    )

    Scaffold(
        topBar = {
            if (topAppBarConfigState.isVisible) {
                TopAppBar(
                    title = {
                        topAppBarConfigState.title?.let { Text(text = it) }
                    },
                    navigationIcon = {
                        topAppBarConfigState.onNavigationIconClick?.let { onClick ->
                            val iconImageVector = when (topAppBarConfigState.navigationIconType) {
                                NavigationIconType.BACK -> Icons.AutoMirrored.Filled.ArrowBack
                                NavigationIconType.MENU -> Icons.Filled.Menu
                                NavigationIconType.CLOSE -> Icons.Filled.Close
                                NavigationIconType.NONE -> null // Explicitly null if no icon
                            }
                            val description = when (topAppBarConfigState.navigationIconType) {
                                NavigationIconType.BACK -> "Back"
                                NavigationIconType.MENU -> "Menu"
                                NavigationIconType.CLOSE -> "Close"
                                NavigationIconType.NONE -> null
                            }
                            iconImageVector?.let { imageVector ->
                                IconButton(onClick = onClick) {
                                    Icon(imageVector, description)
                                }
                            }
                        }
                    },
                    // Corrected actions usage
                    actions = topAppBarConfigState.actions
                )
            }
        },
        bottomBar = {
            NavigationBar {
                val currentDestination = navBackStackEntry?.destination
                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any {
                            val isSelected = if (screen == BottomNavItem.Bookmark) {
                                it.route == BOOKMARKS_GRAPH_ROUTE || currentRoute == BOOKMARK_SCREEN_ROUTE
                            } else {
                                it.route == screen.route
                            }
                            isSelected
                        } == true,
                        onClick = {
                            val targetRoute = if (screen == BottomNavItem.Bookmark) {
                                BOOKMARKS_GRAPH_ROUTE
                            } else {
                                screen.route
                            }
                            mainNavController.navigate(targetRoute) {
                                popUpTo(mainNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        AppNavHost(
            navController = mainNavController,
            appViewModel = appViewModel,
            modifier = Modifier.padding(innerPadding),
            startDestination = AppRoute.MainAppGraph.route
        )
    }
}
