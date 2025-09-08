plugins {
    id("pinterest.android-library-convention")
}

android {
    namespace = "com.vsay.pintereststylegriddemo.feature_home"
}

dependencies {
    implementation(project(":core-navigation"))
    implementation(project(":core-domain"))
    implementation(project(":core-ui"))
    implementation(project(":core-platform-utils"))
    // Add feature-specific dependencies below

    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Coil for image loading
    implementation(libs.coil.compose)

    // Pagination
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)

    // Accompanist
    implementation(libs.accompanist.placeholder.material)

    // navigation
    implementation(libs.androidx.navigation.compose)
}
