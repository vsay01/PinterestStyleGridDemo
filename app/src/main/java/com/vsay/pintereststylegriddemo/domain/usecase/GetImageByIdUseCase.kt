package com.vsay.pintereststylegriddemo.domain.usecase

import com.vsay.pintereststylegriddemo.domain.model.Image
import com.vsay.pintereststylegriddemo.domain.repository.ImageRepository
import javax.inject.Inject

/**
 * Use case for fetching a single image by its unique identifier.
 *
 * This class encapsulates the business logic required to retrieve image details.
 * It depends on [ImageRepository] to access the data layer.
 *
 * @property repository The repository responsible for image data operations.
 */
class GetImageByIdUseCase @Inject constructor(
    private val repository: ImageRepository
) {

    /**
     * Executes the use case to fetch an image by its ID.
     *
     * @param id The unique identifier of the image to fetch.
     * @return The [Image] object if found, or null if no image matches the ID or an error occurs.
     */
    suspend operator fun invoke(id: String): Image? {
        return repository.getImageById(id)
    }
}