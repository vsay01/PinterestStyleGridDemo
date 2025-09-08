package com.vsay.pintereststylegriddemo.feature_bookmarks.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes

fun NavGraphBuilder.bookmarkNavGraph() {
    // If bookmarks feature is just one screen, you can use composable directly.
    // If it's a nested graph of multiple bookmark-related screens:
    navigation(
        startDestination = AppRoutes.Bookmark.BookmarkRoot.route,
        route = AppRoutes.BookmarkGraph.route
    ) {
        composable(route = AppRoutes.Bookmark.BookmarkRoot.route) {
            BookmarkScreen()
        }
        // Add other composables specific to the bookmarks feature here if any
    }
}
