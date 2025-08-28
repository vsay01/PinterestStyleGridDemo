package com.vsay.pintereststylegriddemo.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.vsay.pintereststylegriddemo.presentation.accountsettingoverview.ui.AccountSettingsOverviewScreen
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
import com.vsay.pintereststylegriddemo.presentation.profile.ui.ProfileScreen

/**
 * Defines the navigation graph for the Profile tab.
 *
 * This graph includes the main [ProfileScreen] and a nested navigation graph
 * for account settings.
 *
 * @param navController The [NavHostController] used for navigation.
 * @param appViewModel The [AppViewModel] or other ViewModels as needed, passed to the screens.
 */
fun NavGraphBuilder.profileGraph(
    navController: NavHostController,
    appViewModel: AppViewModel // Or other ViewModels as needed
) {
    navigation(
        route = AppRoute.Profile.ProfileGraph.route, // The main graph for the Profile tab
        startDestination = AppRoute.Profile.ProfileRoot.route
    ) {
        composable(AppRoute.Profile.ProfileRoot.route) {
            ProfileScreen(
                navController = navController,
                appViewModel = appViewModel
                // ... other actions
            )
        }

        // --- Definition of the NESTED Account Settings Graph ---
        navigation(
            route = AppRoute.Profile.AccountSettingsGraph.route, // Route for this nested graph
            startDestination = AppRoute.Profile.AccountSettingsOverview.route
        ) {
            composable(AppRoute.Profile.AccountSettingsOverview.route) {
                AccountSettingsOverviewScreen(
                    navController = navController,
                    appViewModel = appViewModel
                )
            }
            composable(AppRoute.Profile.ChangePassword.route) {
                ChangePasswordScreen(navController = navController, appViewModel = appViewModel)
            }
            composable(AppRoute.Profile.ManageNotifications.route) {
                // ManageNotificationsScreen(...)
            }
        }
        // --- End of Nested Account Settings Graph ---
    }
}
