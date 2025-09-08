package com.vsay.pintereststylegriddemo.presentation.profile.ui // Or your preferred package structure

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vsay.pintereststylegriddemo.R
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
import com.vsay.pintereststylegriddemo.common.TopAppBarConfig
import com.vsay.pintereststylegriddemo.common.NavigationIconType
import com.vsay.pintereststylegriddemo.ui.theme.PinterestStyleGridDemoTheme

const val TAG = "ProfileScreen"

@OptIn(ExperimentalMaterial3Api::class) // For TopAppBar
@Composable
fun ProfileScreen(
    navController: NavController,
    appViewModel: AppViewModel,
) {

    val context = LocalContext.current // Get context for string resources

    LaunchedEffect(Unit) {
        appViewModel.showTopAppBar(
            TopAppBarConfig(
                title = context.getString(R.string.bottom_nav_profile),
                navigationIconType = NavigationIconType.BACK,
                onNavigationIconClick = {
                    navController.navigateUp()
                }
            )
        )
    }

    ProfileScreenUI(
        isLoading = false, // Mocked: Not loading
        error = null,      // Mocked: No error
        onSettingsClick = {
            // Placeholder for other settings navigation
            // e.g., navController.navigate("some_other_settings_route")
            Log.d(TAG, "Other General Settings clicked")
        },
        onGoToAccountSettings = {
            navController.navigate(AppRoutes.Profile.AccountSettingsGraph.route)
        },
        onLogoutClick = {
            // Placeholder for logout logic
            Log.d(TAG, "Logout clicked")
        },
        onRetry = {
            // Placeholder for retry logic
            Log.d(TAG, "Retry clicked")
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
    onRetry: () -> Unit // Kept for completeness, though not actively used in this simplified version
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
                    Spacer(modifier = Modifier.height(8.dp)) // Add Spacer import if needed
                    Button(onClick = onRetry) { Text("Retry") }
                }
            }

            else -> {
                // Simplified content: Just show the buttons
                Column(
                    modifier = Modifier.fillMaxSize(), // Or wrap content if preferred
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center // Center buttons for demo
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
                        Text("Account Settings (Nested Graph)")
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

// Preview for the simplified UI
@Preview(showBackground = true, name = "ProfileScreenUI - Simplified")
@Composable
fun ProfileScreenUISimplifiedPreview() {
    PinterestStyleGridDemoTheme {
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
