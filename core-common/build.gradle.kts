plugins {
    id("pinterest.kotlin-library-convention")
}

// No android {} block needed for a pure Kotlin module

dependencies {
    // Add any specific dependencies for common utilities if needed
    // For example, if you use a specific logging library:
    // implementation(libs.findLibrary("timber.timber").get())
}
