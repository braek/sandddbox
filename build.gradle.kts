/**
 * Needed to use Kotlin in the project
 */
plugins {
    kotlin("jvm") version libs.versions.kotlin.get()
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