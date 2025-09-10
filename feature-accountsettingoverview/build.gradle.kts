plugins {
    id("pinterest.android-library-convention")
}

android {
    namespace = "com.vsay.pintereststylegriddemo.feature_accountsettingoverview"
}

dependencies {
    implementation(project(":core-navigation"))
    implementation(project(":core-domain"))
    implementation(project(":core-ui"))
    // Add feature-specific dependencies below
}
