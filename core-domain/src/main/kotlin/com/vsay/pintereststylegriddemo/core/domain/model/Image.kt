package com.vsay.pintereststylegriddemo.core.domain.model

data class Image(
    val id: String,
    val author: String,
    val url: String,
    val width: Int,
    val height: Int,
    val downloadURL: String
)