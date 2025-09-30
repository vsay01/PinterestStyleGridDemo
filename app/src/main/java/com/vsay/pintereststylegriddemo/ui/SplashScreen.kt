package com.vsay.pintereststylegriddemo.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.vsay.core.authstate.UserStateViewModel

@Composable
fun SplashScreen(
    userStateViewModel: UserStateViewModel = hiltViewModel(),
    onAuthCheckComplete: (isUserLoggedIn: Boolean) -> Unit
) {
    val currentUser by userStateViewModel.currentUser.collectAsStateWithLifecycle()
    val initialAuthDetermined by userStateViewModel.initialAuthDetermined.collectAsStateWithLifecycle()
    var navigationCallbackInvoked by remember { mutableStateOf(false) }

    LaunchedEffect(initialAuthDetermined, currentUser) {
        Log.d(
            "SplashScreen",
            "Effect: initialAuthDetermined=$initialAuthDetermined, currentUser null?=${currentUser == null}, invoked=$navigationCallbackInvoked"
        )
        if (initialAuthDetermined && !navigationCallbackInvoked) {
            Log.d(
                "SplashScreen",
                "Auth determined & not invoked. User: $currentUser. Calling onAuthCheckComplete."
            )
            onAuthCheckComplete(currentUser != null)
            navigationCallbackInvoked = true
        } else if (!initialAuthDetermined) {
            Log.d("SplashScreen", "Waiting for initial auth to be determined...")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}