package com.vsay.pintereststylegriddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.vsay.pintereststylegriddemo.presentation.app.AppViewModel
import com.vsay.pintereststylegriddemo.ui.AppWithTopBar
import com.vsay.pintereststylegriddemo.ui.theme.PinterestStyleGridDemoTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main activity of the application.
 *
 * This activity serves as the entry point and hosts the main UI of the application.
 * It initializes the [AppViewModel] using Hilt's `viewModels()` delegate and sets up the
 * content view using Jetpack Compose. The main UI is encapsulated within the [AppWithTopBar]
 * composable, which receives the [AppViewModel] instance.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val appViewModel: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PinterestStyleGridDemoTheme {
                Surface(modifier = Modifier) {
                    AppWithTopBar(appViewModel = appViewModel)
                }
            }
        }
    }
}
