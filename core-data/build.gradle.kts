plugins {
    id("pinterest.android-library-convention")
}

android {
    namespace = "com.vsay.pintereststylegriddemo.core.data"
}

dependencies {
    implementation(project(":core-domain"))

    // Retrofit + Moshi + Serialization
    implementation(libs.retrofit.core)
    implementation(libs.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.loggingInterceptor)

    // Pagination
    implementation(libs.androidx.paging.runtime)
    implementation(libs.androidx.paging.compose)
}