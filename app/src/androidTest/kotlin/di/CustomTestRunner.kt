package com.vsay.pintereststylegriddemo.testing.fakes.di

import android.app.Application
import android.content.Context
import android.os.Bundle // For onCreate
import android.util.Log  // IMPORT LOG
import androidx.test.runner.AndroidJUnitRunner
import dagger.hilt.android.testing.HiltTestApplication

class CustomTestRunner : AndroidJUnitRunner() {

    // ADDED CONSTRUCTOR LOG
    init {
        Log.d("AuthRepoDebug", "CUSTOM_TEST_RUNNER: Initialized (constructor)")
    }

    // ADDED ONCREATE LOG
    override fun onCreate(arguments: Bundle?) {
        Log.d("AuthRepoDebug", "CUSTOM_TEST_RUNNER: onCreate called with arguments: $arguments")
        super.onCreate(arguments)
    }

    override fun newApplication(
        cl: ClassLoader?,
        className: String?,
        context: Context?
    ): Application {
        // ADDED LOG TO EXISTING METHOD
        Log.d("AuthRepoDebug", "CUSTOM_TEST_RUNNER: newApplication. Requested className: $className. Forcing HiltTestApplication.")
        return super.newApplication(cl, HiltTestApplication::class.java.name, context)
    }
}
