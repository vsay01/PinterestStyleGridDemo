package com.vsay.core.authstate // Adjust to your module's package

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsay.pintereststylegriddemo.core.domain.model.User
import com.vsay.pintereststylegriddemo.core.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserStateViewModel @Inject constructor(
    private val authRepository: AuthRepository // Injected from :core-data via Hilt
) : ViewModel() {

    private val _initialAuthDetermined = MutableStateFlow(false)
    val initialAuthDetermined: StateFlow<Boolean> = _initialAuthDetermined.asStateFlow()

    val currentUser: StateFlow<User?> = authRepository.getAuthStateStream()
        .map { user ->
            if (!_initialAuthDetermined.value) {
                _initialAuthDetermined.value =
                    true // Mark as determined on first emission from stream
                Log.d("UserStateViewModel", "Initial auth state determined by stream. User: $user")
            }
            user
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    init {
        // To handle cases where the stream might take a moment to emit or if WhileSubscribed stops it.
        // This ensures initialAuthDetermined eventually becomes true.
        viewModelScope.launch {
            // currentUser.first() // Await first emission if needed, or rely on map
            // For safety, ensure it becomes true after a short period if stream is slow to start
            // or if no collectors are active initially.
            if (currentUser.value == null && !_initialAuthDetermined.value) { // If initialValue from stateIn is still active
                kotlinx.coroutines.delay(500) // Wait a bit
                if (!_initialAuthDetermined.value) { // If map still hasn't run
                    _initialAuthDetermined.value = true
                    Log.d("UserStateViewModel", "Initial auth determination timed out/forced true.")
                }
            }
        }
    }

    fun signOut() {
        viewModelScope.launch {
            // When signing out, the user will become null.
            // We don't need to reset _initialAuthDetermined here as it's about the *initial* app load.
            authRepository.signOut()
        }
    }
}
