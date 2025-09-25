package com.vsay.pintereststylegriddemo.core.domain.model

sealed interface AuthResult {
    object Success : AuthResult
    data class Error(val errorMessage: String?, val exception: Throwable? = null) : AuthResult
}