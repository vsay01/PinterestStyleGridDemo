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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vsay.pintereststylegriddemo.common.navigation.AppNavHost
import com.vsay.pintereststylegriddemo.common.navigation.AppRoute
import com.vsay.pintereststylegriddemo.common.navigation.BottomNavItem
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
import com.vsay.pintereststylegriddemo.ui.common.NavigationIconType

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
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppWithTopBar(
    appViewModel: AppViewModel,
) {
    val mainNavController = rememberNavController()
    val topAppBarConfig by appViewModel.topAppBarConfig.collectAsState()

    // Define bottom navigation items (ensure BottomNavItem uses ImageVector for icons)
    val bottomNavItems = listOf(
        BottomNavItem.Home,    // Assumes BottomNavItem.Home.route points to a graph route like AppRoute.MainAppGraph.route
        BottomNavItem.Bookmark,  // Assumes BottomNavItem.Search.route points to AppRoute.SearchGraph.route
        BottomNavItem.Profile  // Assumes BottomNavItem.Profile.route points to AppRoute.ProfileGraph.route (or similar)
    )
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
        },
        bottomBar = {
            // Use NavigationBar for Material 3
            NavigationBar {
                val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.label) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any {
                            // Important: Match the graph route if BottomNavItem.route points to a graph
                            it.route == screen.route
                        } == true,
                        onClick = {
                            mainNavController.navigate(screen.route) {
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
        // Pass AppViewModel and NavController to AppNavHost
        // AppNavHost will then pass AppViewModel to individual screens
        AppNavHost(
            navController = mainNavController,
            appViewModel = appViewModel,
            modifier = Modifier.padding(innerPadding),
            startDestination = AppRoute.MainAppGraph.route
        )
    }
}
