package com.vsay.pintereststylegriddemo.data.model

import com.vsay.pintereststylegriddemo.core.domain.model.Image
import kotlin.random.Random

/**
 * Converts an [ImageDto] to an [Image] domain model.
 *
 * This function takes an [ImageDto] and transforms it into an [Image] object,
 * which is used in the domain layer of the application.
 *
 * It generates a random height for the image while keeping the width fixed.
 * The random height is calculated to be between 40% and 100% of the `fixedWidth`.
 * A dynamic download URL is also generated to fetch an image with these new dimensions.
 *
 * @param fixedWidth The desired fixed width for the image. Defaults to 400.
 * @return An [Image] object with a fixed width and a randomized height.
 */
fun ImageDto.toDomain(fixedWidth: Int = 400): Image {
    // Define a range for the random height.
    // For example, height can vary from 40% of fixedWidth to 100% of fixedWidth.
    val minRandomHeight = (fixedWidth * 0.4).toInt()
    val maxRandomHeight = (fixedWidth * 1)
    // Generate a random height within the defined range.
    // Random.nextInt's upper bound is exclusive, so add 1 to maxRandomHeight.
    val randomHeight = Random.nextInt(minRandomHeight, maxRandomHeight + 1)

    // The download URL should request an image with the fixedWidth and the new randomHeight.
    val dynamicUrl = "https://picsum.photos/id/$id/$fixedWidth/$randomHeight"

    return Image(
        id = id,
        author = author,
        width = fixedWidth,      // The width is kept fixed
        height = randomHeight,   // The height is now randomized
        url = url,               // Original URL from the DTO
        downloadURL = dynamicUrl // URL to fetch the image with the desired random dimensions
    )
}
