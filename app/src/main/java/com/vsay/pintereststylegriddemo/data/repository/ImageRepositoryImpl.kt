package com.vsay.pintereststylegriddemo.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.vsay.pintereststylegriddemo.data.paging.ImagePagingSource
import com.vsay.pintereststylegriddemo.data.remote.ApiService
import com.vsay.pintereststylegriddemo.domain.model.Image
import com.vsay.pintereststylegriddemo.domain.repository.ImageRepository

/**
 * Implementation of the [ImageRepository] interface.
 *
 * This class is responsible for fetching images from the [ApiService] and mapping them to domain models.
 * It utilizes the Paging 3 library to provide a paginated list of images.
 *
 * @property apiService The [ApiService] instance used to fetch images. This is injected via Hilt.
 */
class ImageRepositoryImpl(
    private val apiService: ApiService
) : ImageRepository {

    override fun getPagedImages(): Pager<Int, Image> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImagePagingSource(apiService) }
        )
    }
}
