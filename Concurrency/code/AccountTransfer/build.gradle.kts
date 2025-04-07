/*
 * Gradle build configuration for specific lab module / exercise
 */
plugins {
    application
}

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
    mainClass = "account.AccountTransferSimulation"
}
