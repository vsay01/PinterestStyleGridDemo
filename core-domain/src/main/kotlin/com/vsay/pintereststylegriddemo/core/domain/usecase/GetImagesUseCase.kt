package com.vsay.pintereststylegriddemo.core.domain.usecase

import androidx.paging.PagingData
import com.vsay.pintereststylegriddemo.core.domain.model.Image
import com.vsay.pintereststylegriddemo.core.domain.repository.ImageRepository
import kotlinx.coroutines.flow.Flow

/**
 * Use case for retrieving a paginated list of images.
 *
 * This class encapsulates the business logic for fetching images in a paginated manner.
 * It interacts with an [ImageRepository] to retrieve the data as a [Flow] of [PagingData].
 *
 * The `invoke` operator allows this use case to be called like a function, providing a clean
 * and concise way to execute the image fetching logic.
 *
 * @property repository The [ImageRepository] instance used to fetch paginated image data.
 */
class GetImagesUseCase(private val repository: ImageRepository) {
    operator fun invoke(): Flow<PagingData<Image>> {
        return repository.getPagedImages()
    }
}
