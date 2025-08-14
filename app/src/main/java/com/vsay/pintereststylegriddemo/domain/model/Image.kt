package com.vsay.pintereststylegriddemo.domain.model

data class Image(
    val id: String,
    val imageUrl: String,
    val description: String? = null
)
