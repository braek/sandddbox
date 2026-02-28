plugins {
    kotlin("jvm") version libs.versions.kotlin.get()
}

repositories {
    mavenLocal()
    mavenCentral()
}

kotlin {
    jvmToolchain(21)
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "kotlin")

    dependencies {
        /**
         * JUnit API for WRITING test
         */
        testImplementation("org.junit.jupiter:junit-jupiter:${rootProject.testLibs.versions.junit.get()}")
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