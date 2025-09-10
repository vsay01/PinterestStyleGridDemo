package com.vsay.pintereststylegriddemo.analytics

interface HomeAnalyticsTracker {
    fun trackHomeScreenOpened()
    fun trackImageFeedLoaded()
    fun trackImageClicked(imageId: String)
    // Add more home-specific analytics events here
}