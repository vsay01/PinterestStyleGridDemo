package com.vsay.pintereststylegriddemo.common.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import com.vsay.pintereststylegriddemo.R
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes
import com.vsay.pintereststylegriddemo.feature_detail.navigation.detailNavGraph
import com.vsay.pintereststylegriddemo.navigation.homeNavGraph
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
import com.vsay.pintereststylegriddemo.ui.bookmarkNavGraph

/**
 * Composable function that defines the navigation graph for the application.
 *
 * It uses a [NavHost] to manage navigation between different screens.
 *
 * @param navController The [NavHostController] used for navigation.
 * @param appNavigator The [AppNavigatorImpl] for handling navigation actions.
 * @param appViewModel The [AppViewModel] shared across different screens.
 * @param modifier Optional [Modifier] to be applied to the NavHost.
 * @param startDestination The route for the start destination of the graph.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    appNavigator: AppNavigatorImpl, // Added appNavigator parameter
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        homeNavGraph(
            detailNavigator = appNavigator, // Pass IDetailNavigator
            onShowTopAppBar = { topAppBarConfig ->
                appViewModel.showTopAppBar(topAppBarConfig)
            },
            smallIconResId = R.mipmap.ic_launcher
        )
        detailNavGraph(
            navController = navController,
            onShowTopAppBar = { topAppBarConfig ->
                appViewModel.showTopAppBar(topAppBarConfig)
            }
        )
        bookmarkNavGraph()  // Corresponds to BottomNavItem.Bookmark.route
        profileGraph(navController, appViewModel) // Corresponds to BottomNavItem.Profile.route
    }
}
