package com.vsay.pintereststylegriddemo.feature_bookmarks.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes.BOOKMARKS_GRAPH_ROUTE
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes.BOOKMARK_SCREEN_ROUTE

fun NavGraphBuilder.bookmarkNavGraph() {
    // If bookmarks feature is just one screen, you can use composable directly.
    // If it's a nested graph of multiple bookmark-related screens:
    navigation(
        startDestination = BOOKMARK_SCREEN_ROUTE,
        route = BOOKMARKS_GRAPH_ROUTE
    ) {
        composable(route = BOOKMARK_SCREEN_ROUTE) {
            BookmarkScreen()
        }
        // Add other composables specific to the bookmarks feature here if any
    }
}
