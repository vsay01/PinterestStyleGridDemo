package com.vsay.pintereststylegriddemo.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
// REMOVE: AppViewModel and TopAppBarConfig related imports
import com.vsay.pintereststylegriddemo.theme.PinterestStyleGridDemoTheme // Assuming theme is accessible

// You might create a ChangePasswordViewModel later if logic becomes complex
// import com.vsay.pintereststylegriddemo.feature_profile.viewmodel.ChangePasswordViewModel

@Composable
fun ChangePasswordScreen(
    navController: NavController
) {
    // The TopAppBar (title, back button) will be configured by AppWithTopBar in :app module
    // based on the AppRoutes.Profile.ChangePassword.route

    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmNewPassword by remember { mutableStateOf("") }
    // Add state for loading/error if you implement the actual password change logic

    ChangePasswordScreenUI(
        oldPassword = oldPassword,
        onOldPasswordChange = { oldPassword = it },
        newPassword = newPassword,
        onNewPasswordChange = { newPassword = it },
        confirmNewPassword = confirmNewPassword,
        onConfirmNewPasswordChange = { confirmNewPassword = it },
        onChangePasswordClick = {
            // Implement actual password change logic here
            // e.g., call viewModel.changePassword(oldPassword, newPassword)
            // For now, it just navigates back as a placeholder
            if (newPassword == confirmNewPassword && newPassword.isNotBlank()) {
                // SUCCESS (Placeholder)
                navController.popBackStack()
            } else {
                // SHOW ERROR (Placeholder)
            }
        },
        modifier = Modifier
    )
}

@Composable
fun ChangePasswordScreenUI(
    modifier: Modifier = Modifier,
    oldPassword: String,
    onOldPasswordChange: (String) -> Unit,
    newPassword: String,
    onNewPasswordChange: (String) -> Unit,
    confirmNewPassword: String,
    onConfirmNewPasswordChange: (String) -> Unit,
    onChangePasswordClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Change Your Password", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = oldPassword,
            onValueChange = onOldPasswordChange,
            label = { Text("Old Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newPassword,
            onValueChange = onNewPasswordChange,
            label = { Text("New Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmNewPassword,
            onValueChange = onConfirmNewPasswordChange,
            label = { Text("Confirm New Password") },
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onChangePasswordClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Update Password")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChangePasswordScreenPreview() {
    PinterestStyleGridDemoTheme {
        ChangePasswordScreenUI(
            oldPassword = "old",
            onOldPasswordChange = {},
            newPassword = "new",
            onNewPasswordChange = {},
            confirmNewPassword = "new",
            onConfirmNewPasswordChange = {},
            onChangePasswordClick = {}
        )
    }
}
