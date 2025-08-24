package com.vsay.pintereststylegriddemo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
import com.vsay.pintereststylegriddemo.presentation.detail.ui.DetailScreen
import com.vsay.pintereststylegriddemo.presentation.home.ui.HomeScreen

/**
 * Composable function that defines the navigation graph for the application.
 *
 * It uses a [NavHost] to manage navigation between different screens.
 * The `startDestination` is set to [Screen.Home].
 *
 * @param navController The [NavHostController] used for navigation.
 * @param appViewModel The [AppViewModel] shared across different screens.
 * @param modifier Optional [Modifier] to be applied to the NavHost.
 */
@Composable
fun AppNavHost(
    navController: NavHostController,
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                appViewModel = appViewModel,
                // homeViewModel is hiltViewModel() internally
                onImageClick = { image ->
                    navController.navigate(Screen.Detail.createRoute(image.id))
                }
            )
        }
        composable(
            route = Screen.Detail.route,
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
