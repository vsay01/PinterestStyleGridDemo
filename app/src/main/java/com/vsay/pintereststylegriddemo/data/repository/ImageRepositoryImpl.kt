package com.vsay.pintereststylegriddemo.data.repository

import com.vsay.pintereststylegriddemo.data.model.toDomain
import com.vsay.pintereststylegriddemo.data.remote.ApiService
import com.vsay.pintereststylegriddemo.domain.model.Image
import com.vsay.pintereststylegriddemo.domain.repository.ImageRepository

/**
 * Implementation of the [ImageRepository] interface.
 *
 * This class is responsible for fetching images from the [ApiService] and mapping them to domain models.
 *
 * @property apiService The [ApiService] instance used to fetch images.
 */
class ImageRepositoryImpl(
    private val apiService: ApiService
) : ImageRepository {

    override suspend fun getImages(page: Int, limit: Int): List<Image> {
        return apiService.getImages(page, limit).map { it.toDomain() }
    }
}
