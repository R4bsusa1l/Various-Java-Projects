plugins {
    application
    id("org.openjfx.javafxplugin") version "0.1.0"
}

// Project/Module information
description = "FXML Calculator"
group = "fxmlcalculator"
version = "2025"

// Dependency configuration
repositories {
    mavenCentral()
}

dependencies {

}

// Plugin configurations
application {
    mainClass = "fxmlcalculator.Main"
}

javafx {
    version = "21.0.6"
    modules("javafx.controls", "javafx.fxml")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    tasks.compileJava {
        options.encoding = "UTF-8"
    }
}
