package com.vsay.pintereststylegriddemo.testutils

// Conceptual: TestAuthUtils represents a simplified way to manage a fake auth state for testing.
// In a real application using Hilt for dependency injection, you would typically replace
// your production AuthRepository/UserService with a FakeAuthRepository/FakeUserService
// in your :app/src/androidTest/ Hilt module configuration. AuthTestPreconditions
// would then interact with methods on that Hilt-injected fake to set the desired auth state
// before the Journey begins.
object TestAuthUtils {
    private var currentTestUserEmail: String? = null
    val registeredTestUsers = mutableMapOf<String, String>() // email -> password

    fun registerTestUser(email: String, pass: String) {
        registeredTestUsers[email] = pass
    }

    fun loginTestUser(email: String, pass: String): Boolean {
        if (registeredTestUsers.containsKey(email) && registeredTestUsers[email] == pass) {
            currentTestUserEmail = email
            return true
        }
        currentTestUserEmail = null
        return false
    }

    fun logoutTestUser() {
        currentTestUserEmail = null
    }

    fun getCurrentTestUserEmail(): String? = currentTestUserEmail

    fun isUserLoggedIn(email: String): Boolean {
        return currentTestUserEmail == email
    }

    fun clearAllTestUsers() {
        registeredTestUsers.clear()
        currentTestUserEmail = null
    }
}


object AuthTestPreconditions {

    /**
     * Ensures a user is "logged in" for the test using a conceptual fake authentication utility.
     * In a Hilt-based app, this would translate to configuring Hilt to provide a fake
     * authentication service and then using that service to establish a logged-in session.
     * The Journey runner or subsequent steps must ensure the app UI reflects this state.
     *
     * @param context The JourneyContext (conceptual).
     * @param email The email to use for the "logged-in" test user.
     * @param pass The password (can be dummy if auth is faked via TestAuthUtils or a Hilt fake).
     */
    fun ensureUserIsLoggedIn(context: JourneyContext, email: String = "test.user@example.com", pass: String = "password123") {
        // Step 1: "Register" the user in our conceptual fake system if not already there.
        // In a real Hilt setup, your FakeAuthRepository might not need explicit registration
        // if it can dynamically mock any user.
        if (!TestAuthUtils.registeredTestUsers.containsKey(email)) {
            TestAuthUtils.registerTestUser(email, pass)
        }

        // Step 2: "Log in" the user in our conceptual fake system.
        val loggedIn = TestAuthUtils.loginTestUser(email, pass)

        // Step 3: Inform the Journey runner about this action.
        // The actual effect on the app (making ViewModels see a logged-in user)
        // would be because the app, during tests, is using a Hilt-injected fake
        // whose state is being controlled here (indirectly via TestAuthUtils).
        if (loggedIn) {
            context.performAction("Programmatically set app state to: user '${email}' is logged in (using test authentication).")
        } else {
            // This would be a failure in the test setup logic itself.
            context.performAction("ERROR: Failed to programmatically log in test user '${email}' using test authentication. Test might be unreliable.")
        }
    }

    /**
     * Ensures no user is "logged in" for the test using a conceptual fake authentication utility.
     * In a Hilt-based app, this would translate to configuring Hilt to provide a fake
     * authentication service and then using that service to clear any active session.
     */
    fun ensureUserIsLoggedOut(context: JourneyContext) {
        TestAuthUtils.logoutTestUser()
        context.performAction("Programmatically set app state to: user is logged out (using test authentication).")
    }
}
