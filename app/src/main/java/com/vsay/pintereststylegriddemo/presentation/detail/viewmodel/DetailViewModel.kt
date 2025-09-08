package com.vsay.pintereststylegriddemo.presentation.detail.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsay.pintereststylegriddemo.R
import com.vsay.pintereststylegriddemo.core.navigation.NavArgs
import com.vsay.pintereststylegriddemo.core.domain.model.Image
import com.vsay.pintereststylegriddemo.core.domain.usecase.GetImageByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "DetailViewModel"

/**
 * ViewModel for the Detail Screen.
 *
 * This ViewModel is responsible for fetching and managing the state of a single image
 * detail. It retrieves the image ID from the [SavedStateHandle] (passed via navigation arguments)
 * and uses the [GetImageByIdUseCase] to load the corresponding image data.
 *
 * The UI state is exposed as a [StateFlow] of [UiState], which can be observed by the UI
 * to display loading, success (with image data), or error states.
 *
 * It also provides a [retry] function to attempt reloading the image if an error occurred.
 *
 * @property application The application instance, injected by Hilt, used for accessing resources.
 * @property getImageByIdUseCase The use case for fetching image details.
 * @property savedStateHandle A handle to the saved state, used to retrieve navigation arguments.
 */
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
        val idFromArgs: String? = savedStateHandle.get<String>(NavArgs.IMAGE_ID)
        this.currentImageId = idFromArgs // Assign to the class property for later use (e.g., retry)

        if (idFromArgs.isNullOrBlank()) {
            _uiState.value = UiState.Error(application.getString(R.string.error_invalid_or_missing_image_id))
        } else {
            loadImage(idFromArgs)
        }
    }

    /**
     * Loads the image data for the given ID using the [GetImageByIdUseCase].
     * Updates the [uiState] with loading, success, or error states.
     *
     * @param id The unique identifier of the image to load.
     */
    fun loadImage(id: String) {
        if (id != currentImageId) {
            currentImageId = id
        }

        viewModelScope.launch {
            Log.d(TAG, "Loading image with ID: $id")
            _uiState.value = UiState.Loading
            try {
                // Use the use case to fetch the image
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

    /**
     * Retries loading the image using the currently stored imageId.
     * This is useful if a previous attempt failed.
     */
    fun retry() {
        if (currentImageId != null) {
            Log.d(TAG, "Retrying to load image with ID: $currentImageId")
            loadImage(currentImageId!!)
        } else {
            Log.e(TAG, "Cannot retry: Image ID is null.")
            // Use the injected application to get the string
            _uiState.value = UiState.Error(application.getString(R.string.error_invalid_or_missing_image_id))
        }
    }
}

/**
 * Represents the different states of a UI operation that involves fetching data.
 *
 * This sealed class is used to model the possible states of an asynchronous operation
 * (like fetching image details) that updates the UI. It allows for exhaustive handling
 * of these states in `when` expressions, ensuring all cases (Loading, Success, Error)
 * are considered by the UI.
 *
 * @param T The type of data associated with the success state.
 */
sealed class UiState<out T> {
    /** Indicates that the data is currently being loaded. */
    object Loading : UiState<Nothing>()

    /** Indicates that the data was successfully loaded. */
    data class Success<T>(val data: T?) :
        UiState<T>() // data can be null if success means "found nothing" but not an error

    /** Indicates that an error occurred during the data loading operation. */
    data class Error(val message: String) : UiState<Nothing>()
}
