plugins {
    // Apply your convention plugin using the ID you registered
    id("pinterest.android-library-convention")
    alias(libs.plugins.kotlin.compose)
}

android {
    // The namespace is always specific to the module and must remain here.
    namespace = "com.vsay.pintereststylegriddemo.feature.bookmarks"

    // Any configurations that are truly specific to this feature_bookmarks module
    // and either override or supplement what's in the convention plugin would go here.
    // For example, if this module needed a specific buildConfigField:
    // defaultConfig {
    //     buildConfigField("String", "BOOKMARKS_SPECIFIC_FLAG", "\"true\"")
    // }
    // Or if it had unique Proguard rules on top of the common ones:
    // buildTypes {
    //     release {
    //         consumerProguardFiles("bookmarks-specific-consumer-rules.pro")
    //     }
    // }
}

dependencies {
    // Only dependencies that are SPECIFIC to the feature_bookmarks module
    // and are NOT already included by your "pinterest.android-library-convention" convention plugin.
}
