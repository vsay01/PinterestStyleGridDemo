package com.vsay.pintereststylegriddemo.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes
import com.vsay.pintereststylegriddemo.core.navigation.NavArgs
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
import com.vsay.pintereststylegriddemo.presentation.detail.ui.DetailScreen
import com.vsay.pintereststylegriddemo.presentation.home.ui.HomeScreen

/**
 * Defines the main application navigation graph.
 *
 * This graph includes screens like Home and Detail.
 *
 * @param navController The [NavHostController] used for navigation.
 * @param appViewModel The [AppViewModel] shared across the application.
 */
fun NavGraphBuilder.mainAppGraph(navController: NavHostController, appViewModel: AppViewModel) {
    navigation(
        startDestination = AppRoutes.Main.Home.route, // Actual start screen of this graph
        route = AppRoutes.MainAppGraph.route          // The route for this entire graph
    ) {
        composable(AppRoutes.Main.Home.route) {
            HomeScreen(
                appViewModel = appViewModel,
                // homeViewModel is hiltViewModel() internally
                onImageClick = { image ->
                    navController.navigate(AppRoutes.Main.Detail.createRoute(image.id))
                }
            )
        }
        composable(
            route = AppRoutes.Main.Detail.route,
            arguments = listOf(navArgument(NavArgs.IMAGE_ID) {
                type = NavType.StringType
                // nullable = true // Consider if imageId can ever be null, though unlikely for a detail screen
            }),
            deepLinks = listOf(navDeepLink { uriPattern = "myapp://detail/{${NavArgs.IMAGE_ID}}" })
        ) {
            DetailScreen(
                appViewModel = appViewModel,
                navController = navController, // Pass NavController here
                // detailViewModel is hiltViewModel() internally
            )
        }
    }
}
