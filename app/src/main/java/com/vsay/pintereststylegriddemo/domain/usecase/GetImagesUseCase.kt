package com.vsay.pintereststylegriddemo.domain.usecase

import com.vsay.pintereststylegriddemo.domain.model.Image
import com.vsay.pintereststylegriddemo.domain.repository.ImageRepository

class GetImagesUseCase(private val repository: ImageRepository) {
    suspend operator fun invoke(): List<Image> {
        return repository.getImages()
    }
}
