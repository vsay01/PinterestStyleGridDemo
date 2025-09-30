package com.vsay.pintereststylegriddemo.testutils

// Conceptual: 'JourneyContext' is a hypothetical object provided by the Journey runner,
// allowing interaction with the Journey execution engine (e.g., perform UI actions, make assertions).
// Replace with actual Journey tool API calls.
interface JourneyContext {
    fun performAction(description: String)
    fun performVerification(description: String)
    fun getTestParameter(name: String): String? // For parameterized Journeys
}

object SharedJourneySteps {

    // --- Navigation ---
    fun navigateToHomeScreen(context: JourneyContext) {
        context.performAction("Ensure app is on the home screen.") // Might involve bottom nav tap
        context.performVerification("Verify element with test tag 'home_image_grid' is visible.")
    }

    fun navigateToBookmarksScreen(context: JourneyContext) {
        context.performAction("Tap bottom navigation item with label 'Bookmarks'.")
        context.performVerification("Verify element with test tag 'bookmarks_screen_root' is visible.") // Assuming a root tag
    }

    fun navigateToProfileScreen(context: JourneyContext) {
        context.performAction("Tap bottom navigation item with label 'Profile'.")
        context.performVerification("Verify element with test tag 'profile_screen_root' is visible.") // Assuming a root tag for ProfileScreen
    }

    // --- Home Screen ---
    fun selectImageInGridById(context: JourneyContext, imageId: String) {
        context.performAction("Scroll if necessary and tap element with test tag 'home_image_item_${imageId}'.")
    }

    fun selectFirstImageInGrid(context: JourneyContext) {
        context.performAction("Tap element at index 0 in element with test tag 'home_image_grid'.")
    }

    // --- Detail Screen ---
    fun verifyDetailScreenIsVisible(context: JourneyContext, expectedImageId: String? = null) {
        context.performVerification("Verify element with test tag 'image_detail_screen' is visible.")
        expectedImageId?.let {
            context.performVerification("Verify element with test tag 'detail_image_identifier' contains text related to '${it}'.")
        }
    }

    fun tapBookmarkButtonOnDetailScreen(context: JourneyContext) {
        context.performAction("Tap element with test tag 'detail_bookmark_button'.")
    }

    // --- Authentication (New & Demoable) ---
    fun attemptLogin(context: JourneyContext, email: String, pass: String) {
        context.performAction("Ensure app is on the sign-in screen.") // Could be a separate step
        context.performAction("Enter text '${email}' into element with test tag 'signin_email_field'.")
        context.performAction("Enter text '${pass}' into element with test tag 'signin_password_field'.")
        context.performAction("Tap element with test tag 'signin_button'.")
    }

    fun verifyLoginSuccess(context: JourneyContext) {
        // Successful login usually navigates away from auth screens.
        // Home screen is a common destination.
        context.performVerification("Verify element with test tag 'home_image_grid' is visible within 10 seconds.")
    }

    fun verifyLoginFailureMessage(context: JourneyContext, expectedMessage: String? = null) {
        val messageToVerify = expectedMessage ?: "Invalid credentials" // Example default
        context.performVerification("Verify element with test tag 'signin_error_message' is visible and contains text '${messageToVerify}'.")
    }

    fun logoutUser(context: JourneyContext) {
        // This assumes logout is accessible, e.g., from Profile screen
        navigateToProfileScreen(context) // Or a more direct logout if available
        context.performAction("Tap element with test tag 'profile_logout_button'.")
        context.performVerification("Verify element with test tag 'signin_email_field' is visible within 10 seconds.") // Back to sign-in
    }
}