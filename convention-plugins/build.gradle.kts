// convention-plugins/build.gradle.kts
import org.gradle.kotlin.dsl.`kotlin-dsl`

plugins {
    `kotlin-dsl`
}

repositories {
    google()
    mavenCentral()
}

// Access versions from the catalog for these dependencies
dependencies {
    // Assuming your libs.versions.toml has entries like 'agp' and 'kotlinGradlePlugin' in the [versions] block
    // and corresponding entries in the [plugins] or [libraries] block.
    // For build logic dependencies like these, you usually reference the version directly.
    implementation("com.android.tools.build:gradle:${libs.versions.agp.get()}") // Example: if agp version is in the catalog
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}") // Example: if kotlin version is in the catalog
    // Or if you have full library aliases:
    // implementation(libs.android.gradlePlugin)
    // implementation(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "pinterest.android-library-convention"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        // Register other plugins here
    }
}
