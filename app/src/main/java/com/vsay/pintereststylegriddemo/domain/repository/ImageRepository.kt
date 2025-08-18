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
}
