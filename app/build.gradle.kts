plugins {
    id("org.jetbrains.kotlin.jvm") version "1.8.10"
    id("io.ktor.plugin") version "2.2.4"
    application
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}
tasks.jar {
    manifest.attributes["Main-Class"] = "app.AppKt"
}
kotlin {
    jvmToolchain(8)
}
application {
    // Define the main class for the application.
    mainClass.set("app.AppKt")
}
