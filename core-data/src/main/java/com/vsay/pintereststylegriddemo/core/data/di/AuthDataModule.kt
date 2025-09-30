package com.vsay.pintereststylegriddemo.core.data.di

import com.google.firebase.auth.FirebaseAuth
import com.vsay.pintereststylegriddemo.core.data.repository.FirebaseAuthRepositoryImpl
import com.vsay.pintereststylegriddemo.core.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // Provides dependencies at the Application level
abstract class AuthDataModule {

    // Use @Binds for interfaces when the implementation has an @Inject constructor
    @Binds
    @Singleton // Ensure a single instance of the repository
    abstract fun bindAuthRepository(
        firebaseAuthRepositoryImpl: FirebaseAuthRepositoryImpl
    ): AuthRepository

    // Companion object for @Provides functions if needed for concrete types
    // that don't have @Inject constructors or need custom instantiation.
    // In this case, FirebaseAuth.getInstance() is how we get it.
    companion object {
        @Provides
        @Singleton // Firebase Auth instance is effectively a singleton
        fun provideFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }
    }
}
