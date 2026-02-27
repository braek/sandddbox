rootProject.name = "maxi-platform"

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            // Versions
            version("kotlin", "2.3.10")
        }
    }
}

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

include(":vocabulary")
project(":vocabulary").projectDir = file("src/vocabulary")