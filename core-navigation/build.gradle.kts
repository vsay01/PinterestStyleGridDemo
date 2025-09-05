plugins {
    id("pinterest.kotlin-library-convention")
}

// No android {} block needed for a pure Kotlin module

dependencies {
    // Add any specific dependencies for navigation contracts if needed
    // For example, if you decide to include navigation-compose types directly:
    // api(libs.findLibrary("androidx.navigation.compose").get())
    // Using api() if other modules consuming :core-navigation also need these types transitively.
    // Or implementation() if only :core-navigation itself uses them.
}
