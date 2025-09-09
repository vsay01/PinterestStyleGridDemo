package com.vsay.pintereststylegriddemo.feature_detail.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.vsay.pintereststylegriddemo.common.TopAppBarConfig
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes

fun NavGraphBuilder.detailNavGraph(
    navController: NavHostController,
    onShowTopAppBar: (TopAppBarConfig) -> Unit
) {
    composable(
        route = AppRoutes.Main.Detail.route,
        arguments = listOf(navArgument(AppRoutes.Main.Detail.ARG_IMAGE_ID) { // Updated to use AppRoutes
            type = NavType.StringType
        }),
        deepLinks = listOf(navDeepLink { uriPattern = "myapp://detail/{${AppRoutes.Main.Detail.ARG_IMAGE_ID}}" }) // Updated
    ) {
        DetailScreen(
            navController = navController,
            onShowTopAppBar = onShowTopAppBar
        )
    }
}
