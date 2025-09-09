package com.vsay.pintereststylegriddemo.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.navigation
import com.vsay.pintereststylegriddemo.R
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes
import com.vsay.pintereststylegriddemo.core.navigation.navigators.IDetailNavigator // Added import
import com.vsay.pintereststylegriddemo.feature_detail.navigation.detailNavGraph
import com.vsay.pintereststylegriddemo.navigation.homeNavGraph
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel

/**
 * Defines the main application navigation graph.
 *
 * This graph includes screens like Home and Detail.
 *
 * @param navController The [androidx.navigation.NavHostController] used for navigation.
 * @param appViewModel The [com.vsay.pintereststylegriddemo.presentation.app.AppViewModel] shared across the application.
 * @param detailNavigator The navigator for detail screen actions.
 */
fun NavGraphBuilder.mainAppGraph(
    navController: NavHostController, // Still needed for detailNavGraph or other direct NavController uses
    appViewModel: AppViewModel,
    detailNavigator: IDetailNavigator // Added detailNavigator
) {
    navigation(
        startDestination = AppRoutes.Main.Home.route, // Actual start screen of this graph
        route = AppRoutes.MainAppGraph.route          // The route for this entire graph
    ) {
        homeNavGraph(
            detailNavigator = detailNavigator, // Pass IDetailNavigator
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
    }
}
