package com.vsay.pintereststylegriddemo.core.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.vsay.pintereststylegriddemo.core.data.model.toDomain
import com.vsay.pintereststylegriddemo.core.data.remote.ApiService
import com.vsay.pintereststylegriddemo.core.domain.model.Image
import kotlin.collections.map

/**
 * A [PagingSource] that loads images from the [ApiService].
 *
 * This class is responsible for fetching paginated data from the network and providing it to the
 * Paging library. It handles loading of pages, determining previous and next keys, and managing
 * refresh scenarios.
 *
 * @property apiService The [ApiService] instance used to fetch image data.
 */
class ImagePagingSource(
    private val apiService: ApiService
) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getImages(page, params.loadSize)
            val images = response.map { it.toDomain() }

            LoadResult.Page(
                data = images,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (images.isEmpty()) null else page + 1
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition?.let { pos ->
            val page = state.closestPageToPosition(pos)
            page?.prevKey?.plus(1) ?: page?.nextKey?.minus(1)
        }
    }
}

