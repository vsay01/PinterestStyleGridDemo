package com.vsay.pintereststylegriddemo.di

import com.vsay.pintereststylegriddemo.analytics.HomeAnalyticsTracker
import com.vsay.pintereststylegriddemo.analytics.LogcatHomeAnalyticsTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class) // Scoped to ViewModels within this Hilt component tree
abstract class HomeAnalyticsModule {

    @Binds
    @ViewModelScoped // The tracker instance will live as long as the ViewModel requesting it
    abstract fun bindHomeAnalyticsTracker(
        impl: LogcatHomeAnalyticsTracker
    ): HomeAnalyticsTracker
}
