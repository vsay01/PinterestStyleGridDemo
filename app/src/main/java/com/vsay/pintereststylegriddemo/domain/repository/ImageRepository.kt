package com.vsay.pintereststylegriddemo.domain.repository

import androidx.paging.Pager
import com.vsay.pintereststylegriddemo.domain.model.Image

/**
 * Interface defining the contract for accessing image data.
 *
 * This repository provides methods to retrieve images, potentially from
 * different data sources (e.g., network, local cache).
 */
interface ImageRepository {
    /**
     * Retrieves a [Pager] instance for paginated access to images.
     *
     * This function is responsible for creating and returning a Pager object that
     * can be used to load images in a paginated manner. The Pager will handle
     * loading data in chunks as the user scrolls or requests more items.
     *
     * @return A [Pager] of type [Int] (for page keys, typically page numbers) and [Image] (the data type being paged).
     */
    fun getPagedImages(): Pager<Int, Image>

    /**
     * Retrieves a specific image by its unique identifier.
     *
     * This function attempts to find and return an [Image] object
     * corresponding to the provided [id]. If no image with the given
     * ID is found, it returns `null`.
     *
     * This is a suspend function, indicating that it performs asynchronous
     * operations (e.g., network request, database query) and should be
     * called from a coroutine or another suspend function.
     *
     * @param id The unique identifier of the image to retrieve.
     * @return The [Image] object if found, otherwise `null`.
     */
    suspend fun getImageById(id: String): Image?
}
