package com.vsay.pintereststylegriddemo.data.repository

import com.vsay.pintereststylegriddemo.domain.model.Image
import com.vsay.pintereststylegriddemo.domain.repository.ImageRepository

class ImageRepositoryImpl : ImageRepository {
    override suspend fun getImages(): List<Image> {
        return listOf(
            Image("1", "https://picsum.photos/300/500"),
            Image("2", "https://picsum.photos/300/450"),
            Image("3", "https://picsum.photos/300/600"),
            Image("4", "https://picsum.photos/300/400"),
            Image("5", "https://picsum.photos/300/550"),
            Image("6", "https://picsum.photos/300/350"),
            Image("7", "https://picsum.photos/300/500"),
            Image("8", "https://picsum.photos/300/450"),
            Image("9", "https://picsum.photos/300/600"),
            Image("10", "https://picsum.photos/300/400"),
            Image("11", "https://picsum.photos/300/550"),
        )
    }
}