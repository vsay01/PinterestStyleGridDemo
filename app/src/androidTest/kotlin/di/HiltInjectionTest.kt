package com.vsay.pintereststylegriddemo.testing.fakes.di

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vsay.pintereststylegriddemo.core.domain.repository.AuthRepository
import com.vsay.pintereststylegriddemo.testing.fakes.FakeAuthRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest // Marks this as a Hilt-instrumented test
@RunWith(AndroidJUnit4::class) // Standard test runner
class HiltInjectionTest {

    @get:Rule(order = 0) // Hilt rule must run first
    var hiltRule = HiltAndroidRule(this)

    @Inject // Ask Hilt to inject an AuthRepository
    lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        Log.d("AuthRepoDebug", "HiltInjectionTest: Running setUp()")
        hiltRule.inject() // Performs field injection (injects authRepository)
        Log.d(
            "AuthRepoDebug",
            "HiltInjectionTest: authRepository injected: ${authRepository::class.java.simpleName}"
        )
    }

    @Test
    fun verifyFakeAuthRepositoryIsInjected() {
        Log.d("AuthRepoDebug", "HiltInjectionTest: Running test method...")
        // Assertion 1: Check the type directly
        assertTrue(
            "Injected AuthRepository should be FakeAuthRepository",
            authRepository is FakeAuthRepository
        )
        Log.d("AuthRepoDebug", "HiltInjectionTest: Assertion 'is FakeAuthRepository' passed.")

        // Assertion 2 (Optional but good): Add a unique method to FakeAuthRepository
        // and call it here. This is a very strong confirmation.
        // Example (add this method to FakeAuthRepository.kt):
        // fun isThisTheFakeRepository(): Boolean = true
        //
        // if (authRepository is FakeAuthRepository) {
        //     assertTrue("isThisTheFakeRepository() should return true", (authRepository as FakeAuthRepository).isThisTheFakeRepository())
        //     Log.d("AuthRepoDebug", "HiltInjectionTest: Assertion 'isThisTheFakeRepository()' passed.")
        // }
    }
}
