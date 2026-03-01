dependencies {
    implementation(project(":api"))
    implementation(project(":domain"))
    implementation(project(":vocabulary"))
    testImplementation(project(":test-doubles"))
    testImplementation(project(":in-memory-adapter"))
}