package com.vsay.pintereststylegriddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.vsay.pintereststylegriddemo.presentation.ui.HomeScreen
import com.vsay.pintereststylegriddemo.presentation.viewmodel.HomeViewModel
import com.vsay.pintereststylegriddemo.ui.theme.PinterestStyleGridDemoTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * The main activity for the Pinterest Style Grid Demo application.
 *
 * This activity is the entry point of the application and is responsible for setting up the
 * main UI content using Jetpack Compose. It utilizes Hilt for dependency injection to provide
 * the [HomeViewModel] to the [HomeScreen].
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PinterestStyleGridDemoTheme {
                // Hilt provides HomeViewModel automatically
                val homeViewModel: HomeViewModel = hiltViewModel()

                HomeScreen(viewModel = homeViewModel)
            }
        }
    }
}
