plugins {
    alias(libs.plugins.spring.boot)
}

dependencies {
    implementation(project(":api"))
    implementation(project(":vocabulary"))

    /**
     * Spring Boot's own BOM, imported the native Gradle way so starter versions
     * don't need to be repeated here.
     */
    implementation(platform(libs.spring.boot.dependencies))
    implementation(libs.spring.boot.starter.web)
    /**
     * Lets Jackson (de)serialize Kotlin data classes without a no-arg constructor.
     */
    implementation(libs.jackson.module.kotlin)
    implementation(kotlin("reflect"))
}
