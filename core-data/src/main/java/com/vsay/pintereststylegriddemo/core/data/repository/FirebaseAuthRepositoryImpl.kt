package com.vsay.pintereststylegriddemo.core.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.vsay.pintereststylegriddemo.core.domain.model.AuthResult
import com.vsay.pintereststylegriddemo.core.domain.model.User
import com.vsay.pintereststylegriddemo.core.domain.repository.AuthRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject // For Hilt

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    init {
        Log.d("AuthRepoDebug", "FirebaseAuthRepositoryImpl INITIALIZED (REAL)") // <-- ADD THIS
    }

    override fun getAuthStateStream(): Flow<User?> {
        return firebaseAuth.authStateFlow().map { firebaseUser ->
            firebaseUser?.toDomainUser()
        }
    }

    override suspend fun signUp(email: String, password: String): AuthResult {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            // Optionally send verification email here
            AuthResult.Success
        } catch (e: Exception) {
            // Map Firebase exceptions to your domain errors
            AuthResult.Error(e.message, e) // Example: map specific exceptions to specific AuthError types
        }
    }

    override suspend fun signIn(email: String, password: String): AuthResult {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error(e.message, e)
        }
    }

    override suspend fun sendPasswordResetEmail(email: String): AuthResult {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            AuthResult.Success
        } catch (e: Exception) {
            AuthResult.Error(e.message, e)
        }
    }

    override suspend fun signOut() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUserBlocking(): User? {
        return firebaseAuth.currentUser?.toDomainUser()
    }

    private fun FirebaseUser.toDomainUser(): User {
        return User(
            uid = this.uid,
            email = this.email,
            displayName = this.displayName
        )
    }
}

// Helper extension function (can be in a separate file) for observing auth state as Flow
fun FirebaseAuth.authStateFlow(): Flow<FirebaseUser?> = kotlinx.coroutines.flow.callbackFlow {
    val authStateListener = FirebaseAuth.AuthStateListener { auth ->
        trySend(auth.currentUser).isSuccess
    }
    addAuthStateListener(authStateListener)
    awaitClose { removeAuthStateListener(authStateListener) }
}
