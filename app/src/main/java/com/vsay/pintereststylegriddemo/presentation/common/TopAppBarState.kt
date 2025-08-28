package com.vsay.pintereststylegriddemo.presentation.common // Or your chosen package

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import com.vsay.pintereststylegriddemo.ui.common.NavigationIconType

/**
 * Data class representing the configuration state for a composable TopAppBar.
 * This class is used by [com.vsay.pintereststylegriddemo.presentation.app.AppViewModel]
 * to control the appearance and behavior of the main application's TopAppBar.
 *
 * @property title The text to be displayed as the title in the TopAppBar. Null if no title.
 * @property navigationIconType The type of navigation icon to be displayed (e.g., BACK, MENU, CLOSE, NONE).
 * @property onNavigationIconClick Lambda to be executed when the navigation icon is clicked. Null if no action.
 * @property actions A composable lambda defining action items (e.g., [androidx.compose.material3.IconButton])
 *                   to be displayed at the end of the TopAppBar. Defaults to no actions.
 * @property isVisible Controls the visibility of the entire TopAppBar. True if visible, false otherwise.
 */
data class TopAppBarConfig(
    val title: String? = null,
    val navigationIconType: NavigationIconType = NavigationIconType.NONE,
    val onNavigationIconClick: (() -> Unit)? = null,
    val actions: @Composable RowScope.() -> Unit = {},
    val isVisible: Boolean = true // To control visibility of the entire TopAppBar
)
