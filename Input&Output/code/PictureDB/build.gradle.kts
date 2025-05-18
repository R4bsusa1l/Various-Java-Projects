import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    application
}

// Project/Module information
description = "PictureDB"

// Dependency configuration
repositories {
    mavenCentral()
}

val junitVersion = "5.12.+"
val mockitoVersion = "5.15.+"
// Required for dynamic mockito agent fix; see (1) below
val mockitoAgent = configurations.create("mockitoAgent")

dependencies {
    testImplementation("com.github.javaparser:javaparser-core:3.26.4")
    testImplementation(platform("org.mockito:mockito-bom:${mockitoVersion}"))
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation(platform("org.junit:junit-bom:${junitVersion}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    mockitoAgent("org.mockito:mockito-core:${mockitoVersion}") { isTransitive = false }
}

// Plugin configurations
application {
    mainClass = "PictureImport"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    tasks.compileJava {
        options.encoding = "UTF-8"
    }
    sourceSets {
        main {
            java {
                srcDirs("src/main/java")
            }
        }
        test {
            java {
                srcDirs("src/test/java")
            }
        }
    }
}

// Configuration for all test tasks
tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events(PASSED, SKIPPED, FAILED)
    }
    // (1) Fixes Mockito warning about disabled dynamic agent loading in future Java releases
    jvmArgs("-javaagent:${mockitoAgent.asPath}")
}


tasks.withType<JavaExec>() {
    // enable console input when running with gradle
    standardInput = System.`in`
    // set system property to load log configuration using class (takes precedence; if not set or fails, file is used)
    systemProperty("java.util.logging.config.class", "LogConfiguration")
    // set system property to load log configuration from properties
    systemProperty("java.util.logging.config.file", "log.properties")
}
