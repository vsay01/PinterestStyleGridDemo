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

            plugins.apply("com.android.library")
            plugins.apply("org.jetbrains.kotlin.android")

            extensions.configure<LibraryExtension> {
                // 'libs' is now correctly typed as VersionCatalog and available from above

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

                // Get Java version from libs
                val javaVersionFromToml = libs.findVersion("java").get().requiredVersion // e.g., "1.8", "11"
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
                    abortOnError = true // Fail the build on lint errors
                    warningsAsErrors = true
                    checkDependencies = true
                }
            }

            // Configure Kotlin JVM toolchain (JDK for compilation)
            project.extensions.getByType(KotlinAndroidProjectExtension::class.java).jvmToolchain(
                libs.findVersion("jvmTarget").get().requiredVersion.toInt() // e.g., jvmTarget = "8" or "11" in TOML -> results in 8 or 11
            )

            // Configure Kotlin compiler options (bytecode target)
            project.tasks.withType(KotlinCompile::class.java).configureEach {
                compilerOptions {
                    val javaVersionFromTomlForKotlin = libs.findVersion("java").get().requiredVersion // e.g., "1.8", "11"
                    jvmTarget.set(JvmTarget.fromTarget(javaVersionFromTomlForKotlin))
                }
            }

            // Common Dependencies
            dependencies {
                add("implementation", libs.findLibrary("androidx-core-ktx").get())
                add("implementation", libs.findLibrary("androidx-activity-compose").get())

                add("implementation", libs.findLibrary("material").get())
                add("implementation", libs.findLibrary("androidx-material3").get())


                add("implementation", libs.findLibrary("androidx-compose-ui").get())
                add("implementation", libs.findLibrary("androidx-compose-ui-tooling").get())
                add("implementation", libs.findLibrary("androidx-compose-ui-tooling-preview").get())
                add("implementation", libs.findLibrary("androidx-navigation-compose").get())
            }
        }
    }
}
