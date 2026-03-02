plugins {
    java
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.shadow)
    alias(libs.plugins.run.paper)
    alias(libs.plugins.resource.factory)
}

group = "party.morino"
version = project.version.toString()

dependencies {
    implementation(project(":common"))
    implementation(project(":api"))
    compileOnly(libs.paper.api)

    implementation(libs.bundles.commands.paper)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.bundles.coroutines.bukkit)

    // JARにバンドル
    implementation(libs.koin.core)

    // テスト依存関係
    testImplementation(libs.paper.api)
    testImplementation(libs.bundles.junit.jupiter)
    testImplementation(libs.bundles.koin.test)
    testImplementation(libs.mockk)
    testImplementation(libs.mock.bukkit)
}

tasks {
    build {
        dependsOn("shadowJar")
    }
    shadowJar
    test {
        useJUnitPlatform()
        testLogging {
            showStandardStreams = true
            events("passed", "skipped", "failed")
            exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        }
    }
    runServer {
        minecraftVersion("1.21.8")
    }
}

sourceSets.main {
    resourceFactory {
        paperPluginYaml {
            name = rootProject.name
            version = project.version.toString()
            website = "https://github.com/morinoparty/PluginName"
            main = "$group.pluginname.paper.PluginName"
            bootstrapper = "$group.pluginname.paper.PluginNameBootstrap"
            loader = "$group.pluginname.paper.PluginNameLoader"
            apiVersion = "1.21"
        }
    }
}
