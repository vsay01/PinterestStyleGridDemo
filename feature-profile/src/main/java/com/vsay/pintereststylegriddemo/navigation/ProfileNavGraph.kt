package com.vsay.pintereststylegriddemo.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes
import com.vsay.pintereststylegriddemo.ui.AccountSettingsOverviewScreen
import com.vsay.pintereststylegriddemo.ui.ChangePasswordScreen
import com.vsay.pintereststylegriddemo.ui.ProfileScreen

fun NavGraphBuilder.profileGraph(
    navController: NavController,
) {
    navigation(
        route = AppRoutes.ProfileGraph.route, // The route for this entire profile nested graph
        startDestination = AppRoutes.Profile.ProfileRoot.route
    ) {
        composable(AppRoutes.Profile.ProfileRoot.route) {
            // ProfileScreen is now part of :feature-profile
            // It will get UserStateViewModel via @HiltViewModel for logout
            ProfileScreen(navController = navController)
        }

        composable(AppRoutes.Profile.EditProfile.route) {
            // EditProfileScreen would be a new Composable within :feature-profile
            // It might have its own ViewModel.
        }

        // Nested graph for Account Settings within the Profile feature
        navigation(
            route = AppRoutes.Profile.AccountSettingsGraph.route,
            startDestination = AppRoutes.Profile.AccountSettingsOverview.route
        ) {
            composable(AppRoutes.Profile.AccountSettingsOverview.route) {
                // AccountSettingsOverviewScreen is part of :feature-profile
                AccountSettingsOverviewScreen(navController = navController)
            }
            composable(AppRoutes.Profile.ChangePassword.route) {
                // ChangePasswordScreen is part of :feature-profile
                ChangePasswordScreen(navController = navController)
            }
            composable(AppRoutes.Profile.ManageNotifications.route) {
                // ManageNotificationsScreen is part of :feature-profile
            }
            // Add other account settings screens here if any
        }
        // Add other top-level destinations within the profile graph here if any
    }
}