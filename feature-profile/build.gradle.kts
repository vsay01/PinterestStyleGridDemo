plugins {
    id("pinterest.android-library-convention")
}

android {
    namespace = "com.vsay.pintereststylegriddemo.feature_profile"
}

dependencies {
    implementation(project(":core-navigation"))
    implementation(project(":core-domain"))
    implementation(project(":core-ui"))
    implementation(project(":core-platform-utils"))
    implementation(project(":core-auth-state"))
}
