package com.vsay.pintereststylegriddemo.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.vsay.pintereststylegriddemo.BuildConfig
import com.vsay.pintereststylegriddemo.data.remote.ApiService
import com.vsay.pintereststylegriddemo.data.repository.ImageRepositoryImpl
import com.vsay.pintereststylegriddemo.domain.repository.ImageRepository
import com.vsay.pintereststylegriddemo.domain.usecase.GetImagesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

/**
 * AppModule is a Dagger Hilt module that provides dependencies for the application.
 * It is installed in the [SingletonComponent], meaning the provided dependencies will have a singleton scope.
 * This module is responsible for providing instances of Retrofit, ApiService, ImageRepository, and GetImagesUseCase.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**
     * Provides a singleton instance of [Retrofit].
     *
     * This function configures Retrofit with:
     * - A base URL for the API.
     * - An [OkHttpClient] that includes an [HttpLoggingInterceptor] for logging network requests and responses (only in debug builds).
     * - A [MoshiConverterFactory] for JSON serialization and deserialization, using a [Moshi] instance configured with [KotlinJsonAdapterFactory] for Kotlin support.
     *
     * @return A configured [Retrofit] instance.
     */
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
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
        return Retrofit.Builder()
            .baseUrl("https://picsum.photos/v2/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi)) // Pass custom Moshi
            .build()
    }

    /**
     * Provides an instance of [ApiService].
     *
     * This function uses the provided [Retrofit] instance to create and return an implementation
     * of the [ApiService] interface. This allows other parts of the application to make API calls
     * defined in [ApiService].
     *
     * @param retrofit The [Retrofit] instance to use for creating the [ApiService].
     * @return An instance of [ApiService].
     */
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

    /**
     * Provides a singleton instance of [ImageRepository].
     *
     * This function is responsible for creating and providing an instance of [ImageRepository],
     * which is used to interact with the image data source. It depends on an [ApiService]
     * instance to make network requests.
     *
     * @param apiService The [ApiService] instance used to fetch image data.
     * @return A singleton instance of [ImageRepository].
     */
    @Provides
    @Singleton
    fun provideImageRepository(apiService: ApiService): ImageRepository =
        ImageRepositoryImpl(apiService)

    /**
     * Provides a singleton instance of [GetImagesUseCase].
     *
     * This use case is responsible for fetching images from the repository.
     *
     * @param repository The [ImageRepository] to be used by the use case.
     * @return A singleton instance of [GetImagesUseCase].
     */
    @Provides
    @Singleton
    fun provideGetImagesUseCase(repository: ImageRepository): GetImagesUseCase =
        GetImagesUseCase(repository)
}
