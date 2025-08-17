package com.vsay.pintereststylegriddemo.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Data class representing an image object retrieved from the API.
 * This class is used for deserializing JSON responses.
 *
 * @property id The unique identifier of the image.
 * @property author The name of the author of the image.
 * @property width The width of the image in pixels.
 * @property height The height of the image in pixels.
 * @property url The URL where the image can be viewed.
 * @property downloadUrl The URL from which the image can be downloaded.
 *                     The JSON key for this property is "download_url".
 */
@JsonClass(generateAdapter = true)
data class ImageDto(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @Json(name = "download_url") // Assuming the JSON key is download_url
    val downloadUrl: String
)
