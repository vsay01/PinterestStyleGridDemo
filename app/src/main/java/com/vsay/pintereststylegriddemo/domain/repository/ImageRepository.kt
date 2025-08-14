package com.vsay.pintereststylegriddemo.domain.repository

import com.vsay.pintereststylegriddemo.domain.model.Image

interface ImageRepository {
    suspend fun getImages(): List<Image>
}
