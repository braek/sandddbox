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
         * Is this one really needed?
         */
        testImplementation(kotlin("test"))
        /**
         * This one is needed for testing, for sure!
         */
        testImplementation("org.junit.jupiter:junit-jupiter:${rootProject.testLibs.versions.junit.get()}")
    }

    tasks.test {
        useJUnitPlatform()
    }
}