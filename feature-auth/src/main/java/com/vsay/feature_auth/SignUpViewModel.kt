package com.vsay.feature_auth // Adjust package name

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
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    var uiState by mutableStateOf(SignUpScreenUiState())
        private set

    private val _navigationEvent = Channel<SignUpNavigationEvent>()
    val navigationEvent = _navigationEvent.receiveAsFlow()

    fun onEmailChange(newEmail: String) {
        uiState = uiState.copy(email = newEmail, genericErrorMessage = null) // <<< --- ADD THIS
    }

    fun onPasswordChange(newPassword: String) {
        uiState =
            uiState.copy(password = newPassword, genericErrorMessage = null) // <<< --- ADD THIS
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        uiState = uiState.copy(
            confirmPassword = newConfirmPassword,
            genericErrorMessage = null
        ) // <<< --- ADD THIS
    }

    fun onSignUpClicked() {
        if (uiState.email.isBlank()) { // It's good practice to check individual fields first
            uiState = uiState.copy(genericErrorMessage = "Email cannot be empty.")
            return
        }
        if (uiState.password.isBlank()) {
            uiState = uiState.copy(genericErrorMessage = "Password cannot be empty.")
            return
        }
        if (uiState.password != uiState.confirmPassword) {
            uiState = uiState.copy(genericErrorMessage = "Passwords do not match.")
            return
        }
        performSignUp()
    }

    private fun performSignUp() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true, genericErrorMessage = null)
            val result = authRepository.signUp(uiState.email.trim(), uiState.password)
            // Update uiState based on the result
            uiState = when (result) { // Assign the result of when to uiState
                is AuthResult.Success -> {
                    // isLoading will be false from the final copy below,
                    // navigation will be handled by UserStateViewModel observing auth state change
                    uiState.copy(isLoading = false) // Explicitly set isLoading if needed before navigation
                }

                is AuthResult.Error -> {
                    uiState.copy(
                        isLoading = false,
                        genericErrorMessage = result.errorMessage
                            ?: "An unknown sign-up error occurred."
                    )
                }
            }
            // It might be better to handle isLoading=false specifically in each branch of 'when'
            // or ensure it's always set correctly if navigation takes time or if Success branch doesn't immediately navigate.
            // For now, setting it after 'when' also works, but can briefly flicker if Success branch is slow to cause navigation.
            // Let's refine the isLoading = false
            // uiState = uiState.copy(isLoading = false) // This line is okay here if success leads to quick navigation
        }
    }

    fun onNavigateToSignInClicked() {
        viewModelScope.launch {
            _navigationEvent.send(SignUpNavigationEvent.NavigateToSignIn)
        }
    }
}

data class SignUpScreenUiState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isLoading: Boolean = false,
    val genericErrorMessage: String? = null
)

sealed interface SignUpNavigationEvent {
    object NavigateToHome : SignUpNavigationEvent // Or to a post-auth/verification destination
    object NavigateToSignIn : SignUpNavigationEvent
}