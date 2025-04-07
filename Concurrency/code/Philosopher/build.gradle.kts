import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    application
}

// Project/Module information
description = "Lab04 Philosopher"
group = "ch.zhaw.it.prog2"
version = "2025"

// Dependency configuration
repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.+")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.mockito:mockito-core:5.15.+")
    testImplementation("org.mockito:mockito-junit-jupiter:5.15.+")
}

// Plugin configurations
application {
    mainClass = "ch.zhaw.prog2.philosopher.PhilosopherGui"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    tasks.compileJava {
        options.encoding = "UTF-8"
    }
}

// Task configuration
tasks.test {
    useJUnitPlatform()
    testLogging {
        events(FAILED, PASSED, SKIPPED)
    }
}
