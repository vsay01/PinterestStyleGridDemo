package com.vsay.pintereststylegriddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.vsay.pintereststylegriddemo.data.repository.ImageRepositoryImpl
import com.vsay.pintereststylegriddemo.domain.usecase.GetImagesUseCase
import com.vsay.pintereststylegriddemo.presentation.ui.HomeScreen
import com.vsay.pintereststylegriddemo.presentation.viewmodel.HomeViewModel
import com.vsay.pintereststylegriddemo.ui.theme.PinterestStyleGridDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = ImageRepositoryImpl()
        val getImagesUseCase = GetImagesUseCase(repository)
        val homeViewModel = HomeViewModel(getImagesUseCase)

        setContent {
            PinterestStyleGridDemoTheme {
                HomeScreen(homeViewModel)
            }
        }
    }
}
