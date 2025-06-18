import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED

plugins {
    application
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

// Required for dynamic mockito agent fix; see (1) below
val mockitoAgent = configurations.create("mockitoAgent")

dependencies {
    mockitoAgent("org.mockito:mockito-core:5.18.+") { isTransitive = false }
    testImplementation("org.mockito:mockito-junit-jupiter:5.18.+")
    testImplementation("org.junit.jupiter:junit-jupiter:5.12.+")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

// Apply a specific Java toolchain to ease working on different environments.
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    tasks.compileJava {
        options.encoding = "UTF-8"
    }
}

application {
    // Define the main class for the application.
    mainClass = "ch.zhaw.it.prog2.smarthome.HomeApp"
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events(PASSED, SKIPPED, FAILED)
    }
    // (1) Fixes Mockito warning about disabled dynamic agent loading in future Java releases
    jvmArgs("-javaagent:${mockitoAgent.asPath}")
}
