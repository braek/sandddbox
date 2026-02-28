/**
 * Kotlin plugin is needed to use Kotlin in the project
 */
plugins {
    kotlin("jvm") version libs.versions.kotlin.get()
}
/**
 * Here you define what JDK version to compile the Kotlin code to
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
         * JUnit Engine and Launcher for RUNNING tests
         */
        testImplementation("org.junit.platform:junit-platform-engine:${rootProject.testLibs.versions.junit.get()}")
        testImplementation("org.junit.platform:junit-platform-launcher:${rootProject.testLibs.versions.junit.get()}")
    }

    tasks.test {
        useJUnitPlatform()
    }
}