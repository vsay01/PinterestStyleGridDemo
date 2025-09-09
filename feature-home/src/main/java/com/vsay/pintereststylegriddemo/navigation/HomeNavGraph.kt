package com.vsay.pintereststylegriddemo.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.vsay.pintereststylegriddemo.common.TopAppBarConfig
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes
import com.vsay.pintereststylegriddemo.core.navigation.navigators.IDetailNavigator
import com.vsay.pintereststylegriddemo.ui.HomeScreen

fun NavGraphBuilder.homeNavGraph(
    detailNavigator: IDetailNavigator,
    onShowTopAppBar: (TopAppBarConfig) -> Unit,
    smallIconResId: Int
) {
    composable(AppRoutes.Main.Home.route) {
        HomeScreen(
            detailNavigator = detailNavigator,
            onShowTopAppBar = onShowTopAppBar,
            smallIconResId = smallIconResId
        )
    }
}
