package com.vsay.pintereststylegriddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.vsay.pintereststylegriddemo.ui.theme.PinterestStyleGridDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PinterestStyleGridDemoTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                }
            }
        }
    }
}
