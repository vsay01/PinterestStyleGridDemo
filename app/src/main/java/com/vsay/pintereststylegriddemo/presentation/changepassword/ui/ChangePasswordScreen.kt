package com.vsay.pintereststylegriddemo.presentation.changepassword.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vsay.pintereststylegriddemo.common.NavigationIconType
import com.vsay.pintereststylegriddemo.common.TopAppBarConfig
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
import com.vsay.pintereststylegriddemo.theme.PinterestStyleGridDemoTheme

@Composable
fun ChangePasswordScreen(navController: NavController, appViewModel: AppViewModel) {
    LaunchedEffect(Unit) {
        appViewModel.showTopAppBar(
            TopAppBarConfig(
                title = "Change Password",
                navigationIconType = NavigationIconType.BACK,
                onNavigationIconClick = { navController.popBackStack() }
            )
        )
    }
    ChangePasswordUI()
}

@Composable
fun ChangePasswordUI(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Change Password Screen Content", style = MaterialTheme.typography.headlineSmall)
        // ... password change fields ...
    }
}

@Preview(showBackground = true)
@Composable
fun ChangePasswordUIPreview() {
    PinterestStyleGridDemoTheme {
        ChangePasswordUI()
    }
}
