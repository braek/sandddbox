/**
 * Needed to use Kotlin in the project
 */
plugins {
    kotlin("jvm") version libs.versions.kotlin.get()
    /**
     * Registered at the root, so subprojects can apply them without repeating the version,
     * but not applied here — only ```rest-adapter``` needs Spring Boot.
     */
    alias(libs.plugins.spring.boot) apply false
}
/**
 * Specify which JDK to build for
 */
kotlin {
    jvmToolchain(21)
}
/**
 * Settings for subprojects
 */
subprojects {
    apply(plugin = "kotlin")

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        /**
         * JUnit APIs for WRITING tests
         */
        testImplementation("org.junit.jupiter:junit-jupiter:${rootProject.testLibs.versions.junit.get()}")
        testImplementation("org.junit.jupiter:junit-jupiter-params:${rootProject.testLibs.versions.junit.get()}")
        /**
         * API for better assertions
         */
        testImplementation(rootProject.testLibs.assertj)
        /**
         * JUnit Engine and Launcher for RUNNING tests
         */
        testImplementation("org.junit.platform:junit-platform-engine:${rootProject.testLibs.versions.junit.get()}")
        testImplementation("org.junit.platform:junit-platform-launcher:${rootProject.testLibs.versions.junit.get()}")
        /**
         * Our own test doubles
         */
        testImplementation(project(":test-doubles"))
    }

    tasks.test {
        useJUnitPlatform()
    }
}