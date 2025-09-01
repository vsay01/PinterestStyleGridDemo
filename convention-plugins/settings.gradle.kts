rootProject.name = "pinterest-convention-plugins"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") { // You're creating a catalog named "libs" for this included build
            from(files("../gradle/libs.versions.toml")) // Pointing to the TOML file in the root project
        }
    }
}