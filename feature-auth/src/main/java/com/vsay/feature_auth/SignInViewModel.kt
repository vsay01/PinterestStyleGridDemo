package com.vsay.feature_auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vsay.pintereststylegriddemo.core.domain.model.AuthResult
import com.vsay.pintereststylegriddemo.core.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var uiState by mutableStateOf(SignInScreenUiState())
        private set

    private val _navigationEvent = Channel<SignInNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onEmailChange(newEmail: String) {
        uiState = uiState.copy(email = newEmail, genericErrorMessage = null)
    }

    fun onPasswordChange(newPassword: String) {
        uiState = uiState.copy(password = newPassword, genericErrorMessage = null)
    }

    fun onSignInClicked() {
        if (uiState.email.isBlank() || uiState.password.isBlank()) {
            uiState = uiState.copy(genericErrorMessage = "Email and password cannot be empty.")
            return
        }
        performSignIn()
    }

    private fun performSignIn() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, genericErrorMessage = null)
            val result = authRepository.signIn(uiState.email.trim(), uiState.password)
            when (result) {
                is AuthResult.Success -> {
                    // Navigation will be triggered by observer of UserStateViewModel.currentUser changing
                    // Or, you can explicitly navigate here if preferred for this screen.
                    // For now, assume global observer handles navigation post-login.
                    // If explicit navigation from here is needed:
                    // _navigationEvent.send(SignInNavigationEvent.NavigateToHome)
                    // For this example, we'll let the global UserStateViewModel handle the redirect
                    // by AppNavHost observing the current user.
                    // This ViewModel's job is done once sign-in is successful at the repo level.
                }

                is AuthResult.Error -> {
                    uiState = uiState.copy(
                        isLoading = false,
                        genericErrorMessage = result.errorMessage
                            ?: "An unknown sign-in error occurred."
                    )
                }
            }
            // Ensure loading is stopped even if Success branch doesn't explicitly set it,
            // though for sign-in, the screen will likely disappear on success.
            uiState = uiState.copy(isLoading = false)
        }
    }

    fun onNavigateToSignUpClicked() {
        viewModelScope.launch {
            _navigationEvent.send(SignInNavigationEvent.NavigateToSignUp)
        }
    }
}

data class SignInScreenUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val genericErrorMessage: String? = null // For errors not specific to fields
)

sealed interface SignInNavigationEvent {
    object NavigateToSignUp : SignInNavigationEvent
}
