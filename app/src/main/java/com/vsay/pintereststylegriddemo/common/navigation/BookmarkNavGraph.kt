package com.vsay.pintereststylegriddemo.common.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
import com.vsay.pintereststylegriddemo.presentation.bookmark.ui.BookmarkScreen

/**
 * Defines the navigation graph for the Bookmark feature.
 *
 * This graph includes the main [BookmarkScreen].
 *
 * @param navController The [NavHostController] for navigating between screens.
 * @param appViewModel The [AppViewModel] providing shared data and logic for the application.
 */
fun NavGraphBuilder.bookmarkGraph(navController: NavHostController, appViewModel: AppViewModel) {
    navigation(
        startDestination = AppRoute.Bookmark.BookmarkRoot.route, // Define in AppRoute
        route = AppRoute.BookmarkGraph.route // Must match BottomNavItem.Search.route & AppRoute.SearchGraph.route
    ) {
        composable(AppRoute.Bookmark.BookmarkRoot.route) {
            BookmarkScreen(
                navController = navController,
                appViewModel = appViewModel,
            )
        }
    }
}
