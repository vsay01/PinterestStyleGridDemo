package com.vsay.pintereststylegriddemo.feature_detail.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsay.pintereststylegriddemo.core.domain.model.Image
import com.vsay.pintereststylegriddemo.core.domain.usecase.GetImageByIdUseCase
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes
import com.vsay.pintereststylegriddemo.core.ui.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "DetailViewModel"

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val application: Application, // Injected Application context
    private val getImageByIdUseCase: GetImageByIdUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Image>>(UiState.Loading)
    val uiState: StateFlow<UiState<Image>> = _uiState.asStateFlow()

    private var currentImageId: String? = null

    init {
        val idFromArgs: String? = savedStateHandle.get<String>(AppRoutes.Main.Detail.ARG_IMAGE_ID) // Updated usage
        this.currentImageId = idFromArgs

        if (idFromArgs.isNullOrBlank()) {
            _uiState.value = UiState.Error(application.getString(R.string.error_invalid_or_missing_image_id))
        } else {
            loadImage(idFromArgs)
        }
    }

    fun loadImage(id: String) {
        if (id != currentImageId) {
            currentImageId = id
        }

        viewModelScope.launch {
            Log.d(TAG, "Loading image with ID: $id")
            _uiState.value = UiState.Loading
            try {
                val image = getImageByIdUseCase(id)
                if (image != null) {
                    Log.d(TAG, "Successfully loaded image: ${image.id}")
                    _uiState.value = UiState.Success(image)
                } else {
                    Log.w(TAG, "Image not found for ID: $id")
                    _uiState.value = UiState.Error("Image not found.") // Consider using string resource
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to load image with ID: $id", e)
                _uiState.value = UiState.Error("Failed to load image: ${e.localizedMessage}") // Consider using string resource
            }
        }
    }

    fun retry() {
        if (currentImageId != null) {
            Log.d(TAG, "Retrying to load image with ID: $currentImageId")
            loadImage(currentImageId!!)
        } else {
            Log.e(TAG, "Cannot retry: Image ID is null.")
            _uiState.value = UiState.Error(application.getString(R.string.error_invalid_or_missing_image_id))
        }
    }
}

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(val data: T?) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
