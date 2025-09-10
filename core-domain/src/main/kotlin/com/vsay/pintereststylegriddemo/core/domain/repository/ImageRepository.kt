package com.vsay.pintereststylegriddemo.core.domain.repository

import androidx.paging.PagingData
import com.vsay.pintereststylegriddemo.core.domain.model.Image
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun getPagedImages(): Flow<PagingData<Image>>
    suspend fun getImageById(id: String): Image?
}