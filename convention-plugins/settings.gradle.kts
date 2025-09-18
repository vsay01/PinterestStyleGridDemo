pluginManagement {
    repositories {
        gradlePluginPortal() // For plugins like kotlin-dsl itself
        google()
        mavenCentral()
    }
}

rootProject.name = "pinterest-convention-plugins"

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories { // For dependencies of the convention plugins themselves (like AGP, Kotlin Gradle Plugin)
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}