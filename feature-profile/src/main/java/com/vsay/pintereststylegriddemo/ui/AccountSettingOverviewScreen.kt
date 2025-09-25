package com.vsay.pintereststylegriddemo.ui

// Material 3 imports
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vsay.pintereststylegriddemo.core.navigation.AppRoutes
import com.vsay.pintereststylegriddemo.theme.PinterestStyleGridDemoTheme

@Composable
fun AccountSettingsOverviewScreen(
    navController: NavController
    // No AppViewModel here
) {
    // The TopAppBar (title, back button) will be configured by AppWithTopBar in :app module
    // based on the AppRoutes.Profile.AccountSettingsOverview.route

    AccountSettingsOverviewUI(
        onNavigateToChangePassword = {
            navController.navigate(AppRoutes.Profile.ChangePassword.route)
        },
        onNavigateToManageNotifications = {
            navController.navigate(AppRoutes.Profile.ManageNotifications.route)
        },
        onNavigateToEditProfile = {
            // Assuming EditProfile is a sibling in the profile graph, not child of account settings
            navController.navigate(AppRoutes.Profile.EditProfile.route)
        }
        // Add other navigation callbacks as needed
    )
}

@Composable
fun AccountSettingsOverviewUI(
    modifier: Modifier = Modifier,
    onNavigateToChangePassword: () -> Unit,
    onNavigateToManageNotifications: () -> Unit,
    onNavigateToEditProfile: () -> Unit
    // Add other lambdas for other settings
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 8.dp) // Add some padding if top app bar is present
    ) {
        item { SettingsListItem("Edit Profile", onClick = onNavigateToEditProfile) }
        item { SettingsListItem("Change Password", onClick = onNavigateToChangePassword) }
        item { SettingsListItem("Manage Notifications", onClick = onNavigateToManageNotifications) }
        // Add more settings items here
        // item { SettingsListItem("Privacy Settings", onClick = { /* ... */ }) }
        // item { SettingsListItem("Linked Accounts", onClick = { /* ... */ }) }
    }
}

@Composable
fun SettingsListItem(
    title: String,
    onClick: () -> Unit,
    subtitle: String? = null
) {
    Column {
        ListItem(
            headlineContent = { Text(title) },
            supportingContent = { if (subtitle != null) Text(subtitle) },
            trailingContent = {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Go to $title"
                )
            },
            modifier = Modifier.clickable(onClick = onClick)
        )
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AccountSettingsOverviewScreenPreview() {
    PinterestStyleGridDemoTheme {
        // For preview, NavController isn't strictly needed if UI doesn't depend on it
        AccountSettingsOverviewUI(
            onNavigateToChangePassword = {},
            onNavigateToManageNotifications = {},
            onNavigateToEditProfile = {}
        )
    }
}