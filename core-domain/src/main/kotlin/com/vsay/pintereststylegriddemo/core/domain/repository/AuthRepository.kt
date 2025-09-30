package com.vsay.pintereststylegriddemo.core.domain.repository

import com.vsay.pintereststylegriddemo.core.domain.model.AuthResult
import com.vsay.pintereststylegriddemo.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    /**
     * Provides a stream of the current authentication state.
     * Emits the [User] if logged in, or null if logged out.
     */
    fun getAuthStateStream(): Flow<User?>

    /**
     * Attempts to sign up a new user.
     */
    suspend fun signUp(
        email: String,
        password: String /*, other details like displayName */
    ): AuthResult

    /**
     * Attempts to sign in an existing user.
     */
    suspend fun signIn(email: String, password: String): AuthResult

    /**
     * Sends a password reset email to the given email address.
     */
    suspend fun sendPasswordResetEmail(email: String): AuthResult

    /**
     * Signs out the current user.
     */
    suspend fun signOut()

    /**
     * Optionally, a way to get the current user synchronously if absolutely needed,
     * but prefer observing [getAuthStateStream].
     */
    fun getCurrentUserBlocking(): User?
}