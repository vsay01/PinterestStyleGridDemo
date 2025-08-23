package com.vsay.pintereststylegriddemo.presentation.navigation

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

/**
 * Defines the types of navigation icons that can be displayed in the top app bar.
 * Each type corresponds to a specific action or visual representation.
 */
enum class NavigationIconType {
    NONE, // No navigation icon
    BACK,
    MENU,
    CLOSE
}

/**
 * Represents a screen in the application.
 *
 * This sealed class defines the properties of a screen, including its route, title,
 * navigation icon type, and top bar actions.
 *
 * @property route The route of the screen.
 * @property title The title of the screen. Can be null if no title or dynamic.
 * @property navigationIconType The type of navigation icon to display for the screen.
 * @property topBarActions A composable function that defines the actions to display in the top bar.
 */
sealed class Screen(
    val route: String,
    val title: String? = null,
    val navigationIconType: NavigationIconType = NavigationIconType.BACK,
    val topBarActions: @Composable RowScope.() -> Unit = {} // Empty actions by default
) {
    object Home : Screen(
        route = "home_screen",
        title = "Home",
        navigationIconType = NavigationIconType.MENU,
        topBarActions = {
            // Define actions for Home screen here if any
            // e.g., IconButton(onClick = { /* search */ }) { Icon(Icons.Filled.Search, "Search") }
        }
    )

    object Detail : Screen(
        route = "detail_screen/{${NavArgs.IMAGE_ID}}",
        title = "Details",
        navigationIconType = NavigationIconType.BACK,
        topBarActions = {
            // Define actions for Detail screen here if any
            // e.g., IconButton(onClick = { /* share */ }) { Icon(Icons.Filled.Share, "Share") }
        }
    ) {
        fun createRoute(imageId: String): String = "detail_screen/$imageId"
    }
}

/**
 * Object to hold navigation argument keys.
 * This helps in maintaining consistency and avoiding typos when defining navigation routes
 * and retrieving arguments.
 */
object NavArgs {
    const val IMAGE_ID = "image_id"
}
