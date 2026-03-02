dependencies {
    implementation(project(":api"))
    implementation(project(":domain"))
    implementation(project(":use-cases"))
    implementation(project(":queries"))
    implementation(project(":vocabulary"))
    /**
     * For this module we need AssertJ on the regular scope (exceptionally)
     */
    implementation("org.assertj:assertj-core:${rootProject.testLibs.versions.assertj.get()}")
}