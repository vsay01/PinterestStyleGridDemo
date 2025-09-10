package com.vsay.pintereststylegriddemo.ui

import android.util.Log
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vsay.pintereststylegriddemo.common.NavigationIconType
import com.vsay.pintereststylegriddemo.common.TopAppBarConfig
import com.vsay.pintereststylegriddemo.common.navigation.AppNavHost
import com.vsay.pintereststylegriddemo.common.navigation.AppNavigatorImpl
import com.vsay.pintereststylegriddemo.common.navigation.BottomNavItem
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
import com.vsay.pintereststylegriddemo.core.ui.R as CoreUiR

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
    // Instantiate AppNavigatorImpl
    val appNavigator = remember(mainNavController) {
        AppNavigatorImpl(mainNavController)
    }

    val topAppBarConfigState by appViewModel.topAppBarConfig.collectAsState()
    val context = LocalContext.current

    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarVisible = when (currentRoute) {
        AppRoutes.Main.Detail.routeDefinition -> false // Detail screen always hides it

        // --- Profile Section ---
        // The main landing screen of the Profile tab shows the bottom bar.
        AppRoutes.Profile.ProfileRoot.route -> true

        // Deeper screens within the Profile graph hide the bottom bar.
        AppRoutes.Profile.EditProfile.route, // Assuming EditProfile is a child screen
        AppRoutes.Profile.AccountSettingsGraph.route, // Route for the nested graph
        AppRoutes.Profile.AccountSettingsOverview.route,
        AppRoutes.Profile.ChangePassword.route,
        AppRoutes.Profile.ManageNotifications.route -> false

        // Add any other specific child routes of Profile that should hide the bottom bar.
        // Note: AppRoutes.Profile.ProfileGraph.route is the route for the navigation graph itself.
        // Usually, the currentRoute will be one of the composable destinations within that graph.

        // --- Default ---
        // For other main tab destinations (like Home, Bookmarks) and by default.
        else -> true
    }

    // Add this LaunchedEffect to register the listener ONCE.
    LaunchedEffect(mainNavController) {
        mainNavController.addOnDestinationChangedListener { controller, destination, arguments ->
            Log.d("NAV_DEBUG", "DestinationChanged: New Destination = ${destination.route}, Args = $arguments")
            // Log the current back stack description if possible (see helper idea below)
            // For now, this event itself is very valuable.
            Log.d("NAV_DEBUG", "DestinationChanged: Current visible route via controller = ${controller.currentDestination?.route}")
        }
    }


    LaunchedEffect(currentRoute) {
        when (currentRoute) {
            AppRoutes.Bookmark.BookmarkRoot.route -> {
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

            AppRoutes.Main.Home.route -> {
                appViewModel.updateTopAppBar(
                    TopAppBarConfig(
                        title = context.getString(CoreUiR.string.home_screen_title),
                        navigationIconType = NavigationIconType.NONE,
                        isVisible = true
                    )
                )
            }

            AppRoutes.Main.Detail.route -> {
                appViewModel.updateTopAppBar(
                    TopAppBarConfig(
                        title = context.getString(CoreUiR.string.details_screen_title),
                        navigationIconType = NavigationIconType.BACK,
                        onNavigationIconClick = { mainNavController.navigateUp() },
                        isVisible = true
                    )
                )
            }
            // Add other routes from AppRoutes that need specific TopAppBar configurations

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
            if (bottomBarVisible) {
                NavigationBar {
                    val currentDestination = navBackStackEntry?.destination
                    bottomNavItems.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = screen.label) },
                            label = { Text(screen.label) },
                            selected = currentDestination?.hierarchy?.any { destInHierarchy ->
                                // Use generic logic for ALL tabs, including Bookmark
                                destInHierarchy.route == screen.route // screen.route is the GRAPH route
                            } == true,
                            onClick = {
                                val targetRoute = screen.route
                                val popUpToRoute = mainNavController.graph.findStartDestination().id

                                Log.d("NAV_DEBUG", "OnClick: Attempting to navigate to targetRoute = $targetRoute")
                                Log.d("NAV_DEBUG", "OnClick: Current Destination (before nav) = ${mainNavController.currentDestination?.route}")
                                Log.d("NAV_DEBUG", "OnClick: PopUpTo Route = $popUpToRoute")

                                mainNavController.navigate(targetRoute) {
                                    popUpTo(popUpToRoute) {
                                        saveState = true
                                        inclusive = true
                                    }
                                    launchSingleTop = true
                                }
                                // Note: The DestinationChangedListener will fire AFTER this navigation action completes.
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        AppNavHost(
            navController = mainNavController,
            appNavigator = appNavigator, // Pass the appNavigator
            appViewModel = appViewModel,
            modifier = Modifier.padding(innerPadding),
            startDestination = AppRoutes.HomeGraph.route
        )
    }
}
