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
         * Libraries for testing purposes only
         */
        create("testLibs") {
            version("junit", "6.0.3")
        }
    }
}

include(":vocabulary")
project(":vocabulary").projectDir = file("src/vocabulary")

include(":domain")
project(":domain").projectDir = file("src/application/domain")

include(":api")
project(":api").projectDir = file("src/application/api")

include(":use-cases")
project(":use-cases").projectDir = file("src/application/use-cases")

include(":queries")
project(":queries").projectDir = file("src/application/queries")

include(":test-doubles")
project(":test-doubles").projectDir = file("src/test-doubles")