plugins {
    kotlin("jvm") version "2.0.0-Beta1"
    id("com.diffplug.spotless") version "6.23.2"
}

group = "be.vandewalleh"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

spotless {
    kotlin {
        ktlint().setEditorConfigPath("$projectDir/.editorconfig")
    }
}
