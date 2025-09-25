package com.vsay.pintereststylegriddemo.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.vsay.feature_auth.landing.AuthLandingScreen
import com.vsay.feature_auth.signin.SignInScreen
import com.vsay.feature_auth.signup.SignUpScreen
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel


/**
 * Defines the navigation graph for the authentication flow.
 *
 * This graph includes screens for landing, sign-in, and sign-up.
 * It is a nested navigation graph, identified by [AppRoutes.AuthGraph.route].
 *
 * @param navController The [NavHostController] used for navigation.
 */
fun NavGraphBuilder.authGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = AppRoutes.Auth.Landing.route, // First screen in auth flow
        route = AppRoutes.AuthGraph.route                // The "ID" of this nested graph
    ) {
        // Define composables directly here
        composable(AppRoutes.Auth.Landing.route) {
            AuthLandingScreen(
                onNavigateToSignIn = {
                    navController.navigate(AppRoutes.Auth.SignIn.route)
                },
                onNavigateToSignUp = {
                    navController.navigate(AppRoutes.Auth.SignUp.route)
                }
            )
        }
        composable(AppRoutes.Auth.SignIn.route) {
            SignInScreen(
                onNavigateToSignUp = {
                    navController.navigate(AppRoutes.Auth.SignUp.route) {
                        popUpTo(AppRoutes.Auth.SignIn.route) { inclusive = true }
                    }
                }
            )
        }
        composable(AppRoutes.Auth.SignUp.route) {
            SignUpScreen(
                onNavigateToSignIn = {
                    navController.navigate(AppRoutes.Auth.SignIn.route) {
                        popUpTo(AppRoutes.Auth.SignUp.route) { inclusive = true }
                    }
                }
            )
        }
        // composable(AppRoutes.Auth.ForgotPassword.route) { /* ... */ } // Add later
    }
}
