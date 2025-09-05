package com.vsay.pintereststylegriddemo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vsay.pintereststylegriddemo.data.model.toDomain // Ensure this maps to core.domain.model.Image
import com.vsay.pintereststylegriddemo.data.paging.ImagePagingSource // Ensure this uses core.domain.model.Image
import com.vsay.pintereststylegriddemo.data.remote.ApiService
import com.vsay.pintereststylegriddemo.core.domain.model.Image // Updated import
import com.vsay.pintereststylegriddemo.core.domain.repository.ImageRepository // Updated import
import kotlinx.coroutines.flow.Flow

/**
 * Implementation of [ImageRepository] that fetches image data from the [ApiService].
 *
 * @property apiService The remote service to fetch image data from.
 */
class ImageRepositoryImpl(
    private val apiService: ApiService
) : ImageRepository {

    /**
     * Retrieves a [Flow] of [PagingData] that provides paged access to [Image] objects.
     *
     * The Pager is configured with a page size of 10 and placeholders are disabled.
     * It uses [ImagePagingSource] to load data from the [apiService].
     *
     * @return A [Flow] of [PagingData] for [Image] objects, keyed by page number (Int).
     */
    override fun getPagedImages(): Flow<PagingData<Image>> { // Updated return type
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImagePagingSource(apiService) } // This PagingSource needs to produce core.domain.model.Image
        ).flow // Expose the Flow
    }

    /**
     * Fetches a single [Image] by its unique identifier.
     *
     * This function calls the [ApiService.getImageDetailsById] to get the image details
     * and then maps the DTO response to the domain [Image] model.
     *
     * @param id The unique identifier of the image to fetch.
     * @return The [Image] object if found, or null if the image does not exist or an error occurs.
     */
    override suspend fun getImageById(id: String): Image? { // Image type now refers to core.domain.model.Image
        val response = apiService.getImageDetailsById(id)
        return response?.toDomain() // This toDomain() needs to return core.domain.model.Image
    }
}
