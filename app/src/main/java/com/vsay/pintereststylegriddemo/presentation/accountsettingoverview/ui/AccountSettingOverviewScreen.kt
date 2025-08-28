package com.vsay.pintereststylegriddemo.presentation.accountsettingoverview.ui

// Material 3 imports
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vsay.pintereststylegriddemo.common.navigation.AppRoute
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
import com.vsay.pintereststylegriddemo.presentation.common.TopAppBarConfig
import com.vsay.pintereststylegriddemo.ui.common.NavigationIconType

@Composable
fun AccountSettingsOverviewScreen(navController: NavController, appViewModel: AppViewModel) {
    // Configure TopAppBar for this screen if needed, e.g., with a back button
    LaunchedEffect(Unit) {
        appViewModel.showTopAppBar(
            TopAppBarConfig(
                title = "Account Settings",
                navigationIconType = NavigationIconType.BACK, // Assuming you have this enum
                onNavigationIconClick = { navController.popBackStack() }
            )
        )
    }
    AccountSettingsOverviewUI(navController = navController)
}

@Composable
fun AccountSettingsOverviewUI(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Account Settings Overview", style = MaterialTheme.typography.headlineSmall)
        Button(onClick = { navController.navigate(AppRoute.Profile.ChangePassword.route) }) {
            Text("Change Password")
        }
        Button(onClick = { navController.navigate(AppRoute.Profile.ManageNotifications.route) }) {
            Text("Manage Notifications")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountSettingsOverviewUIPreview() {
    AccountSettingsOverviewUI(navController = NavController(androidx.compose.ui.platform.LocalContext.current))
}