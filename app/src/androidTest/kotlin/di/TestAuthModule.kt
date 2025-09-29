package com.vsay.pintereststylegriddemo.testing.fakes.di

import com.vsay.pintereststylegriddemo.core.domain.repository.AuthRepository
import com.vsay.pintereststylegriddemo.testing.fakes.FakeAuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [com.vsay.pintereststylegriddemo.core.data.di.AuthDataModule::class] // This tells Hilt to use this module instead of AuthModule during tests
)
object TestAuthModule {
    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return FakeAuthRepository()
    }
}

/*abstract class TestAuthModule {

    @Binds
    @Singleton // Use the same scope as the one being replaced if applicable (e.g., if real AuthRepository is @Singleton)
    abstract fun bindFakeAuthRepository(
        fakeAuthRepository: FakeAuthRepository // Hilt knows how to create FakeAuthRepository due to @Inject constructor
    ): AuthRepository
}*/
