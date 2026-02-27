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

    tasks.test {
        useJUnitPlatform()
    }
}