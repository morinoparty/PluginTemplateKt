plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
}

group = "party.morino"
version = project.version.toString()

dependencies {
    compileOnly(libs.paper.api)
    compileOnly(libs.velocity.api)
    implementation(libs.kotlinx.serialization.json)
}
