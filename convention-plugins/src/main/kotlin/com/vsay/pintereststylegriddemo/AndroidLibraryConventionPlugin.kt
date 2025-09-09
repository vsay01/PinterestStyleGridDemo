package com.vsay.pintereststylegriddemo

import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile // Added import
import org.jetbrains.kotlin.gradle.dsl.JvmTarget // Added import

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.run {
            // Get the version catalog named "libs"
            val libs = extensions.getByType<VersionCatalogsExtension>()
                .named("libs")

            plugins.apply(libs.findPlugin("android-library").get().get().pluginId)
            plugins.apply(libs.findPlugin("kotlin-android").get().get().pluginId)
            plugins.apply("dagger.hilt.android.plugin") // <-- ADDED Hilt plugin
            plugins.apply("org.jetbrains.kotlin.kapt")    // <-- ADDED Kapt for Hilt
            plugins.apply(libs.findPlugin("kotlin-compose").get().get().pluginId)

            extensions.configure<LibraryExtension> {
                compileSdk = libs.findVersion("compileSdk").get().requiredVersion.toInt()
                defaultConfig {
                    minSdk = libs.findVersion("minSdk").get().requiredVersion.toInt()
                    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                    consumerProguardFiles("consumer-rules.pro")
                }

                buildTypes {
                    release {
                        isMinifyEnabled = false
                        proguardFiles(
                            getDefaultProguardFile("proguard-android-optimize.txt"),
                            "proguard-rules.pro"
                        )
                    }
                }

                val javaVersionFromToml = libs.findVersion("java").get().requiredVersion
                val javaVersionForCompileOptions = JavaVersion.toVersion(javaVersionFromToml)
                compileOptions {
                    sourceCompatibility = javaVersionForCompileOptions
                    targetCompatibility = javaVersionForCompileOptions
                }

                buildFeatures {
                    compose = true
                }

                composeOptions {
                    kotlinCompilerExtensionVersion =
                        libs.findVersion("composeCompiler").get().requiredVersion
                }

                lint {
                    abortOnError = true
                    warningsAsErrors = true
                    checkDependencies = true
                }
            }

            project.extensions.getByType(KotlinAndroidProjectExtension::class.java).jvmToolchain(
                libs.findVersion("jvmTarget").get().requiredVersion.toInt()
            )

            project.tasks.withType(KotlinCompile::class.java).configureEach {
                compilerOptions {
                    val javaVersionFromTomlForKotlin = libs.findVersion("java").get().requiredVersion
                    jvmTarget.set(JvmTarget.fromTarget(javaVersionFromTomlForKotlin))
                }
            }

            // Common Dependencies
            dependencies {
                add("implementation", libs.findLibrary("androidx-core-ktx").get())
                add("implementation", libs.findLibrary("androidx-activity-compose").get())
                add("implementation", libs.findLibrary("androidx-material3").get())

                // Coil for image loading
                add("implementation", libs.findLibrary("coil-compose").get())

                // Accompanist (optional for animations or grid)
                add("implementation", libs.findLibrary("accompanist-systemuicontroller").get())
                add("implementation", libs.findLibrary("accompanist-placeholder-material").get())

                add("implementation", libs.findLibrary("androidx-compose-ui").get())
                add("implementation", libs.findLibrary("androidx-compose-ui-tooling").get())
                add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
                add("implementation", libs.findLibrary("androidx-navigation-compose").get())

                // Hilt Dependencies
                add("implementation", libs.findLibrary("hilt-android").get())
                add("implementation", libs.findLibrary("androidx-hilt-navigation-compose").get())
                add("kapt", libs.findLibrary("hilt-compiler").get())
            }
        }
    }
}
