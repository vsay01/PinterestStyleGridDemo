package com.vsay.pintereststylegriddemo.testing.fakes

import android.util.Log
import com.vsay.pintereststylegriddemo.core.domain.model.AuthResult
import com.vsay.pintereststylegriddemo.core.domain.model.User
import com.vsay.pintereststylegriddemo.core.domain.repository.AuthRepository
import com.vsay.pintereststylegriddemo.testutils.TestAuthUtils.registeredTestUsers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeAuthRepository @Inject constructor() : AuthRepository {

    init {
        Log.d("AuthRepoDebug", "FakeAuthRepository INITIALIZED") // <-- ADD THIS
    }

    private val _currentUserFlow = MutableStateFlow<User?>(null)
    private val registeredFakeUsers = mutableMapOf<String, String>() // email -> password

    override suspend fun signIn(email: String, pass: String): AuthResult {
        return if (registeredFakeUsers.containsKey(email) && registeredFakeUsers[email] == pass) {
            val user = User(uid = "fake-uid-$email", email = email, displayName = "vsay")
            _currentUserFlow.value = user
            AuthResult.Success
        } else {
            AuthResult.Error("Invalid fake credentials", Exception("Invalid fake credentials"))
        }
    }

    override suspend fun signOut() {
        _currentUserFlow.value = null
    }

    override suspend fun sendPasswordResetEmail(email: String): AuthResult {
        return if (registeredFakeUsers.containsKey(email)) {
            // In a fake, we don't actually send an email.
            // We just acknowledge that the request would have been valid.
            AuthResult.Success
        } else {
            AuthResult.Error(
                "User with this email not found in fake repository.",
                Exception("Fake user not found")
            )
        }
    }

    override fun getCurrentUserBlocking(): User? {
        // Returns the current value directly. Useful for non-coroutine contexts
        // if absolutely needed, but Flow is generally preferred.
        return _currentUserFlow.value
    }

    override fun getAuthStateStream(): Flow<User?> {
        // This provides a Flow that emits updates whenever the auth state changes.
        // It's often the same as getCurrentUser() if that already returns a suitable Flow.
        return _currentUserFlow.asStateFlow() // or just _currentUserFlow if StateFlow specifics aren't needed by interface
    }

    override suspend fun signUp(email: String, pass: String): AuthResult {
        if (registeredFakeUsers.containsKey(email)) {
            return AuthResult.Error(
                "Fake user already exists",
                Exception("Fake user already exists")
            )
        }
        registeredFakeUsers[email] = pass
        val user = User(uid = "fake-uid-$email", email = email, displayName = "vsay")
        // For sign-up, you might also want to auto-sign-in the user in the fake
        _currentUserFlow.value = user
        return AuthResult.Success
    }

    // --- Test Control Methods ---
    fun setLoggedInUser(user: User?) {
        _currentUserFlow.value = user
        if (user != null && user.email != null) {
            // Optionally, ensure this user "exists" in our fake registered list
            // if other parts of the app might try to "re-authenticate" against the fake.
            // For simplicity, direct state setting is often enough.
            if (!registeredFakeUsers.containsKey(user.email!!)) {
                registeredTestUsers[user.email!!] = "fakepassword" // Store with a dummy password
            }
        }
    }

    fun clearUsers() {
        registeredFakeUsers.clear()
        _currentUserFlow.value = null
    }
}
    