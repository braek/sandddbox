/**
 * Root project name
 */
rootProject.name = "maxi-platform"


/**
 * Dependencies
 */
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            // Versions
            version("kotlin", "2.2.21")
        }
    }
}


/**
 * Vocabulary
 */
include(":vocabulary")
project(":vocabulary").projectDir = file("src/vocabulary")