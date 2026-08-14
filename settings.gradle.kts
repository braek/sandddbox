rootProject.name = "sandddbox"

dependencyResolutionManagement {
    versionCatalogs {
        /**
         * Regular libraries
         */
        create("libs") {
            version("kotlin", "2.3.21")
        }
        /**
         * Libraries for testing purposes only
         */
        create("testLibs") {
            version("junit", "6.1.3")
            version("assertj", "3.27.7")
            library("assertj", "org.assertj", "assertj-core").versionRef("assertj")
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

include(":rest-adapter")
project(":rest-adapter").projectDir = file("src/frontend-adapters/rest-adapter")