package com.vsay.pintereststylegriddemo.common.permission

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

/**
 * A state holder for managing a specific runtime permission.
 *
 * @property permission The Android permission string (e.g., Manifest.permission.CAMERA).
 * @property hasPermission A mutable state indicating whether the permission is currently granted.
 * @property requestPermission A lambda function to trigger the permission request.
 */
@Immutable
data class PermissionHandlerState(
    val permission: String,
    val hasPermission: MutableState<Boolean>,
    val requestPermission: () -> Unit
)

/**
 * A Composable function that remembers and manages the state for a given runtime permission.
 *
 * It provides a [PermissionHandlerState] which includes the current status of the permission
 * and a lambda to request it.
 *
 * @param permission The Android permission string (e.g., Manifest.permission.POST_NOTIFICATIONS).
 * @param onPermissionGranted Optional callback invoked when the permission is granted by the user.
 * @param onPermissionDenied Optional callback invoked when the permission is denied by the user.
 *                           It receives a lambda function `openAppSettings` which can be called
 *                           to navigate the user to the app's settings screen.
 * @param requiresPermission A boolean indicating if this permission is actually required based on
 *                           runtime conditions (e.g., SDK version). If false, `hasPermission` will
 *                           be considered true by default.
 * @return A [PermissionHandlerState] instance.
 */
@Composable
fun rememberPermissionHandler(
    permission: String,
    onPermissionGranted: () -> Unit = {},
    onPermissionDenied: (openAppSettings: () -> Unit) -> Unit = { _ -> },
    requiresPermission: Boolean = true // By default, assume permission is required
): PermissionHandlerState {
    val context = LocalContext.current

    val hasPermissionState = remember(permission, requiresPermission) {
        mutableStateOf(
            if (!requiresPermission) true
            else ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasPermissionState.value = isGranted
            if (isGranted) {
                onPermissionGranted()
            } else {
                onPermissionDenied { context.launchAppSettings() }
            }
        }
    )

    // If permission status changes externally (e.g., user revokes in settings),
    // this LaunchedEffect will try to update our state.
    // Note: This isn't foolproof for all scenarios but helps for background->foreground changes.
    LaunchedEffect(permission, context) {
        if (requiresPermission) {
            hasPermissionState.value = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        }
    }

    return remember(permission, launcher) {
        PermissionHandlerState(
            permission = permission,
            hasPermission = hasPermissionState,
            requestPermission = {
                if (requiresPermission) {
                    launcher.launch(permission)
                } else {
                    // If permission is not required (e.g., older SDK), treat as granted
                    hasPermissionState.value = true
                    onPermissionGranted()
                }
            }
        )
    }
}

/**
 * Extension function on Context to launch the application's detail settings screen.
 */
fun Context.launchAppSettings() {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", packageName, null)
    }
    startActivity(intent)
}
