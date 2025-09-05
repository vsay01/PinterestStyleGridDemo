package com.vsay.pintereststylegriddemo

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

class KotlinLibraryConventionPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.run {
            pluginManager.apply("org.jetbrains.kotlin.jvm")

            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

            extensions.getByType<KotlinJvmProjectExtension>().apply {
                jvmToolchain(libs.findVersion("jvmTarget").get().requiredVersion.toInt())
            }
            
            project.tasks.withType(KotlinCompile::class.java).configureEach {
                compilerOptions {
                    val javaVersionFromTomlForKotlin = libs.findVersion("java").get().requiredVersion
                    jvmTarget.set(JvmTarget.fromTarget(javaVersionFromTomlForKotlin))
                    // Consider adding other common Kotlin compiler arguments here if needed
                    // freeCompilerArgs.addAll(
                    //     "-Xopt-in=kotlin.RequiresOptIn",
                    // )
                }
            }

            dependencies {
                add("implementation", libs.findLibrary("kotlinx-coroutines-core").get())
            }
        }
    }
}
