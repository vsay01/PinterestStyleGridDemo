plugins {
    id("pinterest.android-library-convention")
    alias(libs.plugins.kotlin.compose) // For Jetpack Compose
}

android {
    namespace = "com.vsay.pintereststylegriddemo.core.ui"
    // Add any :core-ui specific configurations here if needed
}

dependencies {
    // Dependencies already included by the convention plugin (like Compose BOM, material3, etc.)
    // don't need to be repeated unless you need a specific version for this module only.

    // Add other UI specific dependencies if any, for example:
    // implementation(libs.findLibrary("coil.compose").get()) // For image loading
    // implementation(libs.findLibrary("androidx.lifecycle.viewmodel.compose").get())
}
