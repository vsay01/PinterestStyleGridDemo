package com.vsay.feature_auth.landing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vsay.pintereststylegriddemo.theme.PinterestStyleGridDemoTheme

@Composable
fun AuthLandingScreen(
    onNavigateToSignIn: () -> Unit,
    onNavigateToSignUp: () -> Unit,
    // modifier: Modifier = Modifier // Optional
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Welcome!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onNavigateToSignIn) {
            Text("Sign In")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onNavigateToSignUp) {
            Text("Sign Up")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AuthLandingScreenPreview() {
    // You might need to wrap with your app's theme for previews to look right
    PinterestStyleGridDemoTheme {
        AuthLandingScreen(onNavigateToSignIn = {}, onNavigateToSignUp = {})
    }
}
