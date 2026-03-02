plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = "party.morino"
version = project.version.toString()

dependencies {
    compileOnly(libs.paper.api)
    implementation(kotlin("stdlib-jdk8"))
    implementation(libs.kotlinx.serialization.json)
}
