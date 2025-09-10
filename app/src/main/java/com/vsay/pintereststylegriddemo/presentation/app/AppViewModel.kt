package com.vsay.pintereststylegriddemo.presentation.app

import androidx.lifecycle.ViewModel
import com.vsay.pintereststylegriddemo.common.TopAppBarConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * A [ViewModel] responsible for managing shared UI state across the application,
 * particularly for UI elements like the TopAppBar that are managed centrally but
 * configured by individual screens.
 */
@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {

    private val _topAppBarConfig = MutableStateFlow(TopAppBarConfig(isVisible = false))
    /**
     * A [kotlinx.coroutines.flow.StateFlow] emitting the current [TopAppBarConfig] to be displayed.
     * Screens or a central UI manager can collect this flow to update the TopAppBar's
     * title, navigation icon, actions, and visibility.
     * Defaults to a hidden TopAppBar configuration.
     */
    val topAppBarConfig = _topAppBarConfig.asStateFlow()

    /**
     * Updates the entire [TopAppBarConfig] with the provided configuration.
     * Use this function when all aspects of the TopAppBar (title, icons, actions, visibility)
     * need to be set or changed.
     *
     * @param config The new [TopAppBarConfig] to apply.
     */
    fun updateTopAppBar(config: TopAppBarConfig) {
        _topAppBarConfig.value = config
    }

    /**
     * Shows the TopAppBar using the provided configuration, ensuring its `isVisible` flag is true.
     * If you want to update the TopAppBar and ensure it's visible, this method is preferred
     * over [updateTopAppBar] if the only change needed regarding visibility is to show it.
     *
     * @param config The [TopAppBarConfig] to apply, which will be marked as visible.
     */
    fun showTopAppBar(config: TopAppBarConfig) {
        _topAppBarConfig.value = config.copy(isVisible = true)
    }

    /**
     * Hides the TopAppBar by setting its `isVisible` flag to false,
     * while retaining the rest of the current [TopAppBarConfig] (like title or actions).
     * This is useful for screens that do not require a TopAppBar.
     */
    fun hideTopAppBar() {
        _topAppBarConfig.value = _topAppBarConfig.value.copy(isVisible = false)
    }
}
