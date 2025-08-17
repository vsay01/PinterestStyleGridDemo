package com.vsay.pintereststylegriddemo.domain.repository

import com.vsay.pintereststylegriddemo.domain.model.Image

/**
 * Repository interface for fetching images.
 * This interface defines the contract for image data sources.
 */
interface ImageRepository {
    suspend fun getImages(page: Int = 1, limit: Int = 20): List<Image>
}
