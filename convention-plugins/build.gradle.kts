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
    implementation("com.android.tools.build:gradle:${libs.versions.agp.get()}") // if agp version is in the catalog
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}") // if kotlin version is in the catalog
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "pinterest.android-library-convention"
            implementationClass = "com.vsay.pintereststylegriddemo.AndroidLibraryConventionPlugin"
        }
        register("kotlinLibrary") {
            id = "pinterest.kotlin-library-convention"
            implementationClass = "com.vsay.pintereststylegriddemo.KotlinLibraryConventionPlugin"
        }
        // Register other plugins here
    }
}
