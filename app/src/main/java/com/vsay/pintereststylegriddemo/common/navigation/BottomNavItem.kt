// In BottomNavItem.kt (or AppRoute.kt if nested there)
package com.vsay.pintereststylegriddemo.common.navigation // Or your chosen package

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.vsay.pintereststylegriddemo.R

/**
 * Represents an item in the bottom navigation bar.
 *
 * Each item has a route to navigate to, a fallback label (primarily for development or if
 * string resources are missing), an icon, and a string resource ID for the localized label.
 *
 * This is a sealed class, meaning all possible bottom navigation items are defined as nested objects
 * (e.g., [Home], [Bookmark], [Profile]).
 *
 * @property route The navigation route associated with this item. This should correspond to a
 *                 destination or graph route defined in the app's navigation setup (e.g., in `AppNavHost`).
 * @property label A fallback string label for the item. The primary label should be sourced from `labelResId`.
 * @property icon The [ImageVector] to be displayed for this item.
 * @property labelResId The Android string resource ID for the localized label of this item.
 */
sealed class BottomNavItem(
    val route: String,      // This route should match a graph route in AppNavHost (e.g., AppRoute.MainAppGraph.route)
    val label: String,
    val icon: ImageVector,
    val labelResId: Int,
) {
    object Home : BottomNavItem(
        route = AppRoute.MainAppGraph.route, // Example: Points to the main content graph
        label = "Home", // Fallback label, actual label will come from string resource
        icon = Icons.Filled.Home,
        labelResId = R.string.bottom_nav_home
    )

    object Bookmark : BottomNavItem(
        route = AppRoute.BookmarkGraph.route, // Placeholder: Define AppRoute.SearchGraph.route
        label = "Bookmark", // Fallback label
        icon = Icons.Filled.Favorite, // Consider changing to Bookmark icon
        labelResId = R.string.bottom_nav_bookmark
    )

    object Profile : BottomNavItem(
        route = AppRoute.ProfileGraph.route, // Placeholder: Define AppRoute.ProfileGraph.route
        label = "Profile", // Fallback label
        icon = Icons.Filled.Person, // Or Icons.Filled.AccountCircle
        labelResId = R.string.bottom_nav_profile
    )
}