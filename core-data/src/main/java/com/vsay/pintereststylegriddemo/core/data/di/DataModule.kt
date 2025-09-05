package com.vsay.pintereststylegriddemo.core.data.di

import com.vsay.pintereststylegriddemo.core.data.repository.ImageRepositoryImpl
import com.vsay.pintereststylegriddemo.core.domain.repository.ImageRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Repositories are typically singletons
abstract class DataModule {

    @Binds
    @Singleton // Ensure the binding provides a singleton instance
    abstract fun bindImageRepository(
        imageRepositoryImpl: ImageRepositoryImpl
    ): ImageRepository
}
