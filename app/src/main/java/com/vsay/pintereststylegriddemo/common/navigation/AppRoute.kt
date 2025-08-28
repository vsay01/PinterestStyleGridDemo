package com.vsay.pintereststylegriddemo.common.navigation

/**
 * Represents the different navigation routes within the application.
 * This sealed class provides a structured way to define and manage navigation paths.
 *
 * Each route is an object or a nested sealed class, inheriting from `AppRoute`.
 * Routes can be simple strings or can include arguments, which are handled by their respective definitions.
 *
 * The `routeDefinition` property holds the string representation of the route, which is used by the
 * navigation component. For routes with arguments, a `createRoute` factory method is typically provided
 * to construct the full route string with the necessary arguments.
 *
 * This class is organized into logical sections based on feature areas or navigation graphs:
 * - **Common Graph Routes**: Top-level navigation graphs.
 * - **Routes within Home/Main Feature Area**: Screens accessible from the main application area.
 * - **Routes for bookmark Feature Area**: Screens related to bookmarks.
 * - **Routes within Profile Feature Area**: Screens related to user profiles, including nested graphs.
 * - **Routes within a Settings Feature Area/Graph**: Screens related to application settings.
 *
 * The `route` property provides convenient access to the `routeDefinition`.
 */
sealed class AppRoute(open val routeDefinition: String) {

    // --- Common Graph Routes ---
    object MainAppGraph : AppRoute("main_app_graph")
    object BookmarkGraph : AppRoute("bookmark_graph")
    object ProfileGraph : AppRoute("profile_graph")


    // --- Routes within Home/Main Feature Area ---
    sealed class Main(override val routeDefinition: String) : AppRoute(routeDefinition) {
        object Home : Main("home")
        object Detail : Main("") { // Pass a dummy/placeholder string to the super constructor
            // Define the base path for this route
            private const val BASE_PATH = "detail_screen"

            // Single source of truth for the argument name
            const val ARG_IMAGE_ID = "image_id"

            override val routeDefinition: String = "$BASE_PATH/{$ARG_IMAGE_ID}"

            // Factory method to create the concrete route with an argument
            // Also uses BASE_PATH for consistency
            fun createRoute(imageId: String): String = "$BASE_PATH/$imageId"
        }
    }

    // --- Routes for bookmark Feature Area (for bookmarkGraph) ---
    sealed class Bookmark(override val routeDefinition: String) : AppRoute(routeDefinition) {
        object BookmarkRoot : Bookmark("bookmark_root")
    }

    // --- Routes within Profile Feature Area ---
    sealed class Profile(override val routeDefinition: String) : AppRoute(routeDefinition) {
        companion object { // For the main Profile graph route
            private const val PROFILE_GRAPH_ROUTE = "profile_graph"
        }
        object ProfileGraph : Profile(PROFILE_GRAPH_ROUTE) // Route for the entire Profile tab's graph

        object ProfileRoot : Profile("profile_root_screen") // Start screen of ProfileGraph
        object EditProfile : Profile("profile_edit_screen") // Another screen in ProfileGraph

        // --- Nested Graph for Account Settings ---
        object AccountSettingsGraph : Profile("profile_account_settings_graph") // Route for the nested graph

        object AccountSettingsOverview : Profile("profile_account_settings_overview_screen") // Start of nested
        object ChangePassword : Profile("profile_change_password_screen")
        object ManageNotifications : Profile("profile_manage_notifications_screen")
    }

    // --- Routes within a Settings Feature Area/Graph ---
    sealed class Settings(
        override val routeDefinition: String // Make this 'override val'
    ) : AppRoute(routeDefinition) {
        object SettingsRoot : Settings("settings")
        object AccountSettings : Settings("settings/account")

        object ChangePassword : Settings("") { // Dummy super constructor
            private const val BASE_PATH = "settings/account/change_password"
            const val ARG_FLOW_ID = "flowId"

            override val routeDefinition: String = "$BASE_PATH/{$ARG_FLOW_ID}"

            fun createRoute(flowId: String): String = "$BASE_PATH/$flowId"
        }

        object NotificationSettings : Settings("settings/notifications")
    }

    val route: String get() = this.routeDefinition
}
