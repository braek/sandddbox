plugins {
    kotlin("jvm") version libs.versions.kotlin.get()
}

kotlin {
    jvmToolchain(21)
}

subprojects {
    apply(plugin = "kotlin")

    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        /**
         * JUnit API for WRITING test
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