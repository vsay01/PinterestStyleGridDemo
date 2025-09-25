package com.vsay.feature_auth.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vsay.feature_auth.SignInNavigationEvent
import com.vsay.feature_auth.SignInViewModel
import com.vsay.pintereststylegriddemo.theme.PinterestStyleGridDemoTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    onNavigateToSignUp: () -> Unit,
    // onNavigateToHome: () -> Unit, // Navigation to home is now handled globally
) {
    val uiState = viewModel.uiState

    LaunchedEffect(key1 = Unit) { // Use Unit or true if events are continuous, or viewModel.navigationEvent for specific restart
        viewModel.navigationEvent.collectLatest { event ->
            when (event) {
                is SignInNavigationEvent.NavigateToSignUp -> onNavigateToSignUp()
                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Sign In", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uiState.email,
            onValueChange = viewModel::onEmailChange,
            label = { Text("Email") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = uiState.password,
            onValueChange = viewModel::onPasswordChange,
            label = { Text("Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = viewModel::onSignInClicked,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign In")
            }
        }

        uiState.genericErrorMessage?.let {
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(24.dp))
        TextButton(onClick = viewModel::onNavigateToSignUpClicked) {
            Text("Don't have an account? Sign Up")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    PinterestStyleGridDemoTheme {
        SignInScreen(
            // Provide a mock/preview ViewModel if necessary for complex previews,
            // otherwise Hilt will try to provide the real one which might not work in previews.
            // For simple previews like this, often not needed unless ViewModel has complex init.
            onNavigateToSignUp = {}
        )
    }
}

