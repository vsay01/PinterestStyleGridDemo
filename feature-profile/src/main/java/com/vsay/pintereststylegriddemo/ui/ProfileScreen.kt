package com.vsay.pintereststylegriddemo.ui // <<< --- UPDATED PACKAGE

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vsay.core.authstate.UserStateViewModel
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes
import com.vsay.pintereststylegriddemo.theme.PinterestStyleGridDemoTheme

const val TAG = "ProfileScreen" // This TAG is fine

// OptIn for ExperimentalMaterial3Api can be removed if no other M3 component here strictly requires it
// after removing TopAppBarConfig related code. Buttons, Text etc. are stable.
@Composable
fun ProfileScreen(
    navController: NavController,
    userStateViewModel: UserStateViewModel = hiltViewModel()
) {

    // The TopAppBar title and navigation icon will now be handled by AppWithTopBar
    // in the :app module, based on the current route.

    ProfileScreenUI(
        isLoading = false, // Mocked: Not loading
        error = null,      // Mocked: No error
        onSettingsClick = {
            Log.d(TAG, "Other General Settings clicked")
            // Potentially navigate to a general settings screen if that's a separate feature/route
        },
        onGoToAccountSettings = {
            // This navigation stays, as it's internal to the profile feature's flow
            navController.navigate(AppRoutes.Profile.AccountSettingsGraph.route)
        },
        onLogoutClick = {
            Log.d(TAG, "Logout clicked by user")
            userStateViewModel.signOut()
            // Navigation after logout is handled globally by MainActivity observing UserStateViewModel
        },
        onRetry = {
            Log.d(TAG, "Retry clicked")
            // Add actual retry logic if this screen can load data
        }
    )
}

@Composable
fun ProfileScreenUI(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    error: String?,
    onSettingsClick: () -> Unit,
    onGoToAccountSettings: () -> Unit,
    onLogoutClick: () -> Unit,
    onRetry: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        when {
            isLoading -> {
                CircularProgressIndicator()
            }

            error != null -> {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("Error: $error", color = MaterialTheme.colorScheme.error)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = onRetry) { Text("Retry") }
                }
            }

            else -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "User Profile Screen", // Placeholder title
                        style = MaterialTheme.typography.headlineMedium,
                        modifier = Modifier.padding(bottom = 24.dp)
                    )
                    Button(
                        onClick = onGoToAccountSettings,
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        Text("Account Settings") // Simpler text for now
                    }
                    Button(onClick = onSettingsClick) { Text("Other General Settings") }
                    Button(onClick = onLogoutClick, modifier = Modifier.padding(top = 8.dp)) {
                        Text("Log Out")
                    }
                }
            }
        }
    }
}

// Previews remain largely the same, they don't depend on the removed AppViewModel.
@Preview(showBackground = true, name = "ProfileScreenUI - Simplified")
@Composable
fun ProfileScreenUISimplifiedPreview() {
    PinterestStyleGridDemoTheme { // Ensure your theme is accessible (e.g., from :core-ui)
        ProfileScreenUI(
            isLoading = false,
            error = null,
            onSettingsClick = {},
            onGoToAccountSettings = {},
            onLogoutClick = {},
            onRetry = {}
        )
    }
}

@Preview(showBackground = true, name = "ProfileScreenUI - Loading")
@Composable
fun ProfileScreenUILoadingPreview() {
    PinterestStyleGridDemoTheme {
        ProfileScreenUI(
            isLoading = true,
            error = null,
            onSettingsClick = {},
            onGoToAccountSettings = {},
            onLogoutClick = {},
            onRetry = {}
        )
    }
}
