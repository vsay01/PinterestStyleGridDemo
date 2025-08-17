package com.vsay.pintereststylegriddemo.domain.usecase

import com.vsay.pintereststylegriddemo.domain.model.Image
import com.vsay.pintereststylegriddemo.domain.repository.ImageRepository

/**
 * Use case for retrieving a list of images.
 *
 * This class encapsulates the business logic for fetching images. It interacts with
 * an [ImageRepository] to retrieve the data.
 *
 * @property repository The [ImageRepository] instance used to fetch image data.
 */
class GetImagesUseCase(private val repository: ImageRepository) {
    suspend operator fun invoke(page: Int = 1, limit: Int = 20): List<Image> {
        return repository.getImages(page, limit)
    }
}
