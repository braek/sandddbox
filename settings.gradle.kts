rootProject.name = "maxi-platform"

dependencyResolutionManagement {
    versionCatalogs {
        /**
         * Regular libraries
         */
        create("libs") {
            version("kotlin", "2.3.10")
        }
        /**
         * Libraries for testing
         */
        create("testLibs") {
            version("junit", "6.0.3")
        }
    }
}

include(":vocabulary")
project(":vocabulary").projectDir = file("src/vocabulary")