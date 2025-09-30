package com.vsay.pintereststylegriddemo.testharnesses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.vsay.pintereststylegriddemo.feature_detail.ui.DetailScreen
import com.vsay.pintereststylegriddemo.theme.PinterestStyleGridDemoTheme

class ImageDetailTestHarnessActivity : ComponentActivity() {
    companion object {
        const val EXTRA_IMAGE_ID = "EXTRA_IMAGE_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val imageId = intent.getStringExtra(EXTRA_IMAGE_ID)
        val finalImageId = imageId ?: run {
            "0"
        }

        setContent {
            PinterestStyleGridDemoTheme {
                val dummyNavController = rememberNavController()

                DetailScreen(
                    navController = dummyNavController,
                    onShowTopAppBar = {}
                )
            }
        }
    }
}