package com.vsay.pintereststylegriddemo.feature_bookmarks.ui

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

// Define a route for this feature's graph, if it has multiple screens.
// If it's just one screen, this might not be a nested graph.
const val BOOKMARKS_GRAPH_ROUTE = "bookmarks_graph"
const val BOOKMARK_SCREEN_ROUTE = "bookmark_screen_route"

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
