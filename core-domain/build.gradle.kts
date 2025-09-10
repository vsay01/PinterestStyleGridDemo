plugins {
    id("pinterest.kotlin-library-convention")
}

dependencies {
    api(libs.androidx.paging.common)
    // Specific dependencies for domain models if any (e.g., kotlinx-datetime if needed for complex date types)
    // Coroutines 'core' and 'test' are already included by the convention plugin.
}