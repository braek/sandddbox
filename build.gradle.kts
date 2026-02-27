plugins {
    kotlin("jvm") version libs.versions.kotlin.get()
}

kotlin {
    jvmToolchain(21)
}

subprojects {
    apply(plugin = "kotlin")

    tasks.test {
        useJUnitPlatform()
    }

    repositories {
        mavenCentral()
        mavenLocal()
    }
}