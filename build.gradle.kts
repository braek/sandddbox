plugins {
    kotlin("jvm") version libs.versions.kotlin.get()
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

subprojects {
    apply(plugin = "kotlin")

    tasks.test {
        useJUnitPlatform()
    }
}