package com.vsay.pintereststylegriddemo.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vsay.pintereststylegriddemo.common.TopAppBarConfig
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes
import com.vsay.pintereststylegriddemo.core.navigation.navigators.IDetailNavigator
import com.vsay.pintereststylegriddemo.ui.HomeScreen

fun NavGraphBuilder.homeNavGraph(
    detailNavigator: IDetailNavigator,
    onShowTopAppBar: (TopAppBarConfig) -> Unit,
    smallIconResId: Int
) {
    navigation(
        startDestination = AppRoutes.Main.Home.route,
        route = AppRoutes.HomeGraph.route
    ) {
        composable(AppRoutes.Main.Home.route) {
            HomeScreen(
                detailNavigator = detailNavigator,
                onShowTopAppBar = onShowTopAppBar,
                smallIconResId = smallIconResId
            )
        }
    }
}
