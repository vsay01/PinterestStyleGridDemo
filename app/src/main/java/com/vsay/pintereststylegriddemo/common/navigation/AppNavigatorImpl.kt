package com.vsay.pintereststylegriddemo.common.navigation

import androidx.navigation.NavHostController
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes
import com.vsay.pintereststylegriddemo.core.navigation.navigators.IDetailNavigator

class AppNavigatorImpl(private val navController: NavHostController) : IDetailNavigator {

    override fun navigateToDetail(imageId: String) {
        navController.navigate(AppRoutes.Main.Detail.createRoute(imageId))
    }

    // Future navigator interface implementations can be added here
    // For example, if you create an IProfileNavigator:
    // override fun navigateToProfile() {
    //     navController.navigate(AppRoutes.Profile.ProfileRoot.route) // Or ProfileGraph.route
    // }
}