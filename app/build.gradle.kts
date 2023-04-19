plugins {
    kotlin("jvm") version "1.8.20"
    kotlin("plugin.serialization") version "1.8.20"
    id("io.ktor.plugin") version "2.2.4"
    id("jacoco")
    id("org.jetbrains.kotlin.plugin.noarg") version "1.8.20"

    application
}
jacoco {
    toolVersion = "0.8.7"
    reportsDirectory.set(layout.buildDirectory.dir("coverage"))

}
tasks.withType<JacocoReport> {
    reports {
        xml.required.set(true)
        csv.required.set(true)
        html.required.set(false)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.neo4j:neo4j-ogm-core:4.0.5")
    runtimeOnly("org.neo4j:neo4j-ogm-bolt-driver:4.0.5")
    implementation("org.postgresql:postgresql:42.6.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.0")
    implementation("org.jetbrains.exposed", "exposed-core", "0.40.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.40.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.40.1")

}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jar {
    manifest.attributes["Main-Class"] = "app.AppKt"
}
noArg {
    annotation("org.neo4j.ogm.annotation.NodeEntity")
    annotation("org.neo4j.ogm.annotation.RelationshipEntity")
}

kotlin {
    jvmToolchain(8)
}
application {
    // Define the main class for the application.
    mainClass.set("app.AppKt")
}
