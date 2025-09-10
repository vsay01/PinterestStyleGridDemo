package com.vsay.pintereststylegriddemo.analytics

import android.util.Log
import javax.inject.Inject

class LogcatHomeAnalyticsTracker @Inject constructor() : HomeAnalyticsTracker {

    private val TAG = "HomeAnalytics"

    override fun trackHomeScreenOpened() {
        Log.d(TAG, "Home screen opened")
    }

    override fun trackImageFeedLoaded() {
        Log.d(TAG, "Image feed loaded")
    }

    override fun trackImageClicked(imageId: String) {
        Log.d(TAG, "Image clicked: ID = $imageId")
    }
}