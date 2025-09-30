package com.vsay.pintereststylegriddemo.core.navigation

object MainAppDestinations {
    const val SPLASH_ROUTE = "splash" // Route for your new Splash Screen
    const val MAIN_CONTENT_ROUTE = "main_content_graph" // Route for the graph that hosts AppWithTopBar
    val AUTH_GRAPH_ROUTE = AppRoutes.AuthGraph.route // Reuse the existing auth graph route string
    const val AUTH_GRAPH_ROUTE_PREFIX = "auth_graph" // Ensure this matches the start of AUTH_GRAPH_ROUTE
}
