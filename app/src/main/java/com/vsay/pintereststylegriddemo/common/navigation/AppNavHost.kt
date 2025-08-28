package com.vsay.pintereststylegriddemo.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel

/**
 * Composable function that defines the navigation graph for the application.
 *
 * It uses a [NavHost] to manage navigation between different screens.
 *
 * @param navController The [NavHostController] used for navigation.
 * @param appViewModel The [AppViewModel] shared across different screens.
 * @param modifier Optional [Modifier] to be applied to the NavHost.
 * @param startDestination The route for the start destination of the graph.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        mainAppGraph(navController, appViewModel) // Corresponds to BottomNavItem.Home.route
        bookmarkGraph(navController, appViewModel)  // Corresponds to BottomNavItem.Search.route
        profileGraph(navController, appViewModel) // Corresponds to BottomNavItem.Profile.route
    }
}
