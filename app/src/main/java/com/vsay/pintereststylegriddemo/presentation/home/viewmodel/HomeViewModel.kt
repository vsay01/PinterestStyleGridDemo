package com.vsay.pintereststylegriddemo.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vsay.pintereststylegriddemo.core.domain.model.Image
import com.vsay.pintereststylegriddemo.core.domain.usecase.GetImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

/**
 * ViewModel for the Home screen.
 *
 * This ViewModel is responsible for fetching and managing the list of images
 * to be displayed on the Home screen. It uses [GetImagesUseCase] to retrieve
 * the data and exposes it as a [Flow] of [PagingData].
 *
 * @property getImagesUseCase The use case responsible for fetching the images.
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getImagesUseCase: GetImagesUseCase
) : ViewModel() {

    /**
     * A cold Flow of paged image data.
     * cachedIn(viewModelScope) ensures the paging state survives configuration changes
     * (like rotation).
     */
    val pagingFlow: Flow<PagingData<Image>> =
        getImagesUseCase.invoke().cachedIn(viewModelScope)
}
