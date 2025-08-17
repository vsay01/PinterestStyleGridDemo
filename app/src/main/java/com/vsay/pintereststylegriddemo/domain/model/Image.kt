package com.vsay.pintereststylegriddemo.domain.model // Or your actual package

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Represents an image.
 *
 * This data class is designed to be easily parceled for Android components (e.g., Activities, Fragments)
 * and serialized/deserialized for JSON communication, typically with a backend API.
 *
 * @property id The unique identifier of the image.
 * @property author The name of the author or creator of the image.
 * @property url The direct URL to the image resource.
 * @property width The width of the image in pixels.
 * @property height The height of the image in pixels.
 * @property downloadURL The URL from which the image can be downloaded. This field is mapped from the JSON key "download_url".
 */
@Parcelize                               // Makes it Parcelable
data class Image(
    val id: String,
    val author: String,
    val url: String,
    val width: Int,
    val height: Int,
    val downloadURL: String
) : Parcelable // Implement Parcelable interface
