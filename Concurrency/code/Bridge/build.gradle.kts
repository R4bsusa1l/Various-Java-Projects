/*
 * Gradle build configuration for specific lab module / exercise
 */
plugins {
    application
}

// Project/Module information
description = "Lab04 Bridge"
group = "ch.zhaw.it.prog2"
version = "2025"

// Dependency configuration
repositories {
    mavenCentral()
}

dependencies {

}

// Plugin configurations
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    tasks.compileJava {
        options.encoding = "UTF-8"
    }
}

application {
    mainClass = "ch.zhaw.prog2.bridge.Main"
}
