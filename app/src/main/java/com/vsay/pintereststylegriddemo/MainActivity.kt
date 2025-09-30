package com.vsay.pintereststylegriddemo

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.vsay.core.authstate.UserStateViewModel
import com.vsay.pintereststylegriddemo.common.navigation.authGraph
import com.vsay.pintereststylegriddemo.core.navigation.MainAppDestinations
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
import com.vsay.pintereststylegriddemo.theme.PinterestStyleGridDemoTheme
import com.vsay.pintereststylegriddemo.ui.AppWithTopBar
import com.vsay.pintereststylegriddemo.ui.SplashScreen
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main activity of the application.
 *
 * This activity serves as the entry point and hosts the main UI of the application.
 * It initializes the [AppViewModel] using Hilt's `viewModels()` delegate and sets up the
 * content view using Jetpack Compose. The main UI is encapsulated within the [AppWithTopBar]
 * composable, which receives the [AppViewModel] instance.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PinterestStyleGridDemoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val mainNavController = rememberNavController()
                    val appViewModel: AppViewModel = hiltViewModel()
                    val userStateViewModel: UserStateViewModel =
                        hiltViewModel() // <<<--- GET UserStateViewModel

                    val currentUser by userStateViewModel.currentUser.collectAsStateWithLifecycle() // <<<--- OBSERVE currentUser
                    val navBackStackEntry by mainNavController.currentBackStackEntryAsState() // <<<--- OBSERVE current route
                    val currentRoute = navBackStackEntry?.destination?.route

                    // This LaunchedEffect will react to changes in currentUser
                    // It will navigate to the correct top-level destination if the user logs in or out
                    // AFTER the splash screen has done its initial work.
                    LaunchedEffect(currentUser, currentRoute) {
                        Log.d(
                            "MainActivityAuth",
                            "currentUser: $currentUser, currentRoute: $currentRoute"
                        )
                        if (currentRoute == MainAppDestinations.SPLASH_ROUTE) {
                            // SplashScreen is handling its own logic based on initial currentUser state
                            return@LaunchedEffect
                        }

                        if (currentUser != null && currentRoute?.startsWith(MainAppDestinations.AUTH_GRAPH_ROUTE_PREFIX) == true) {
                            // User is logged in AND currently in the auth flow
                            Log.d(
                                "MainActivityAuth",
                                "User logged in, navigating to Main Content from Auth Graph"
                            )
                            mainNavController.navigate(MainAppDestinations.MAIN_CONTENT_ROUTE) {
                                popUpTo(MainAppDestinations.AUTH_GRAPH_ROUTE) { inclusive = true }
                                launchSingleTop = true
                            }
                        } else if (currentUser == null && currentRoute == MainAppDestinations.MAIN_CONTENT_ROUTE) {
                            // User is logged out AND currently in the main content flow
                            Log.d(
                                "MainActivityAuth",
                                "User logged out, navigating to Auth Graph from Main Content"
                            )
                            mainNavController.navigate(MainAppDestinations.AUTH_GRAPH_ROUTE) {
                                popUpTo(MainAppDestinations.MAIN_CONTENT_ROUTE) { inclusive = true }
                                launchSingleTop = true
                            }
                        }
                    }

                    NavHost(
                        navController = mainNavController,
                        startDestination = MainAppDestinations.SPLASH_ROUTE
                    ) {
                        composable(MainAppDestinations.SPLASH_ROUTE) {
                            SplashScreen { isUserLoggedIn ->
                                Log.d(
                                    "MainActivityAuth",
                                    "SplashScreen onAuthCheckComplete: isUserLoggedIn=$isUserLoggedIn"
                                )
                                val targetRoute = if (isUserLoggedIn) {
                                    MainAppDestinations.MAIN_CONTENT_ROUTE
                                } else {
                                    MainAppDestinations.AUTH_GRAPH_ROUTE
                                }
                                mainNavController.navigate(targetRoute) {
                                    popUpTo(MainAppDestinations.SPLASH_ROUTE) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        }

                        composable(MainAppDestinations.MAIN_CONTENT_ROUTE) {
                            AppWithTopBar(appViewModel = appViewModel)
                        }

                        authGraph(navController = mainNavController)
                    }
                }
            }
        }
    }
}
