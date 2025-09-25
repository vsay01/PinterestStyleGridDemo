plugins {
    id("pinterest.android-library-convention") // Your existing convention plugin for libraries
    // Ensure Hilt is effectively applied by your convention plugin for annotation processing.
    // If your convention plugin doesn't add 'kotlin-kapt' or 'com.google.devtools.ksp',
    // and 'com.google.dagger.hilt.android', you might need to add them here or adjust the convention.
}

android {
    namespace = "com.yourpackage.core.authstate" // Use the package name you chose
    // compileSdk, minSdk, etc., are likely managed by your convention plugin
}

dependencies {
    implementation(project(":core-domain")) // ESSENTIAL: To access AuthRepository interface and User domain model

    // AndroidX ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose) // Or just libs.androidx.lifecycle.viewmodel
    // if not using viewModel() directly in compose functions *within this module*

    // Coroutines (for viewModelScope and Flow manipulation)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android) // Specifically for viewModelScope

    // Hilt (ensure these are present if not fully covered by your convention plugin for library modules)
    implementation(libs.hilt.android)
    // The Hilt compiler (kapt or ksp) needs to be applied for @HiltViewModel and @Inject
    // This is typically handled by your convention plugin applying 'kotlin-kapt'/'ksp' and 'com.google.dagger.hilt.android'
}