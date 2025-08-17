package com.vsay.pintereststylegriddemo.data.remote

import com.vsay.pintereststylegriddemo.data.model.ImageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    /**
     * Fetches a list of images from the API.
     *
     * @param page The page number to fetch. Defaults to 1.
     * @param limit The number of images per page. Defaults to 20.
     * @return A list of [ImageDto] objects.
     */
    @GET("list")
    suspend fun getImages(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): List<ImageDto>
}
