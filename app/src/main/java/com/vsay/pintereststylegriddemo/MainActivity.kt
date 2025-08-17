package com.vsay.pintereststylegriddemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vsay.pintereststylegriddemo.data.remote.ApiService
import com.vsay.pintereststylegriddemo.data.repository.ImageRepositoryImpl
import com.vsay.pintereststylegriddemo.domain.usecase.GetImagesUseCase
import com.vsay.pintereststylegriddemo.presentation.ui.HomeScreen
import com.vsay.pintereststylegriddemo.presentation.viewmodel.HomeViewModel
import com.vsay.pintereststylegriddemo.ui.theme.PinterestStyleGridDemoTheme
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * The main activity for the Pinterest Style Grid Demo application.
 *
 * This activity is responsible for setting up the Retrofit instance for network requests,
 * initializing the [HomeViewModel] with its dependencies (use case and repository),
 * and displaying the [HomeScreen] Composable.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        // 1. Create the HttpLoggingInterceptor
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            // Set the desired log level.
            // Level.BODY logs headers and body for both request and response.
            // Level.HEADERS logs only headers.
            // Level.BASIC logs basic info (request line and response line).
            // Level.NONE logs nothing.
            level = if (BuildConfig.DEBUG) { // Only log in debug builds
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        // 2. Create the OkHttpClient and add the interceptor
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        // 3. Create Moshi instance with KotlinJsonAdapterFactory
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        // 4. Build Retrofit with the custom OkHttpClient and custom Moshi instance
        val retrofit = Retrofit.Builder()
            .baseUrl("https://picsum.photos/v2/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // Pass custom Moshi
            .build()

        val apiService = retrofit.create(ApiService::class.java)
        val repository = ImageRepositoryImpl(apiService)
        val getImagesUseCase = GetImagesUseCase(repository)

        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return HomeViewModel(getImagesUseCase) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

        setContent {
            PinterestStyleGridDemoTheme {
                val viewModel: HomeViewModel = viewModel(factory = viewModelFactory)
                HomeScreen(viewModel)
            }
        }
    }
}
