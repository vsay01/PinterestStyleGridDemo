package com.vsay.pintereststylegriddemo.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.vsay.pintereststylegriddemo.R
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes
// import com.vsay.pintereststylegriddemo.core.navigation.NavArgs // Removed
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
// import com.vsay.pintereststylegriddemo.presentation.detail.ui.DetailScreen // Old import
import com.vsay.pintereststylegriddemo.ui.HomeScreen // Assuming this is from feature-home or app
import com.vsay.pintereststylegriddemo.feature_detail.ui.detailNavGraph // New import

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
                onImageClick = { image ->
                    navController.navigate(AppRoutes.Main.Detail.createRoute(image.id))
                },
                onShowTopAppBar = { topAppBarConfig ->
                    appViewModel.showTopAppBar(topAppBarConfig)
                },
                smallIconResId = R.mipmap.ic_launcher, // This R is from :app
            )
        }
        detailNavGraph(
            navController = navController,
            onShowTopAppBar = { topAppBarConfig ->
                appViewModel.showTopAppBar(topAppBarConfig)
            }
        )
    }
}
