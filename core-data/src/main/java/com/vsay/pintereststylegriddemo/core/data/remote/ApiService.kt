package com.vsay.pintereststylegriddemo.core.data.remote // Updated package

import com.vsay.pintereststylegriddemo.core.data.model.ImageDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    /**
     * Fetches a list of images from the API.
     *
     * @param page The page number to fetch. Defaults to 1.
     * @param limit The number of images per page. Defaults to 20.
     * @return A list of [ImageDto] objects.
     */
    @GET("v2/list")
    suspend fun getImages(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): List<ImageDto>

    /**
     * Fetches details for a specific image by its ID from Picsum.
     * Endpoint: https://picsum.photos/id/{ID}/info
     *
     * @param imageId The ID of the image to fetch.
     * @return An [ImageDto] object containing the image details, or null if not found/error.
     */
    @GET("id/{imageId}/info")
    suspend fun getImageDetailsById(@Path("imageId") imageId: String): ImageDto?
}
