package com.vsay.pintereststylegriddemo.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsay.pintereststylegriddemo.domain.model.Image
import com.vsay.pintereststylegriddemo.domain.usecase.GetImagesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel for the Home screen.
 *
 * This ViewModel is responsible for fetching and managing the list of images
 * to be displayed on the Home screen. It uses [GetImagesUseCase] to retrieve
 * the data and exposes the UI state through a [kotlinx.coroutines.flow.StateFlow].
 *
 * @property getImagesUseCase The use case responsible for fetching the images.
 */
class HomeViewModel(
    private val getImagesUseCase: GetImagesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Image>>>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadImages()
    }


    /**
     * Loads images from the [GetImagesUseCase] and updates the UI state.
     *
     * This function launches a coroutine in the [viewModelScope] to perform the asynchronous image loading.
     * It first sets the UI state to [UiState.Loading].
     * Then, it attempts to fetch the images using [getImagesUseCase].
     * If successful, it updates the UI state to [UiState.Success] with the loaded images.
     * If an error occurs during the process, it updates the UI state to [UiState.Error]
     * with the error message.
     */
    private fun loadImages() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val images = getImagesUseCase()
                _uiState.value = UiState.Success(images)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "Unknown error")
            }
        }
    }
}

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
