import org.jetbrains.compose.desktop.application.dsl.TargetFormat


repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

plugins {
    kotlin("jvm") version "1.8.0"
    id("org.jetbrains.compose") version "1.4.0"
    kotlin("plugin.serialization") version "1.8.0"
    id("jacoco")
    id("org.jetbrains.kotlin.plugin.noarg") version "1.8.0"
}

dependencies {
    testImplementation(kotlin("test"))
    testImplementation(platform("org.junit:junit-bom:5.9.2"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation("org.neo4j:neo4j-ogm-core:4.0.5")
    runtimeOnly("org.neo4j:neo4j-ogm-bolt-driver:4.0.5")
    implementation("org.postgresql:postgresql:42.5.4")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("org.jetbrains.exposed", "exposed-core", "0.40.1")
    implementation("org.jetbrains.exposed", "exposed-dao", "0.40.1")
    implementation("org.jetbrains.exposed", "exposed-jdbc", "0.40.1")
    implementation(compose.desktop.currentOs)
    implementation(compose.material3)
    implementation(compose.materialIconsExtended)
}

jacoco {
    toolVersion = "0.8.7"
    reportsDirectory.set(layout.buildDirectory.dir("coverage"))
}

tasks.withType<JacocoReport> {
    reports {
        xml.required.set(true)
        csv.required.set(true)
        html.required.set(true)
    }
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

noArg {
    annotation("org.neo4j.ogm.annotation.NodeEntity")
    annotation("org.neo4j.ogm.annotation.RelationshipEntity")
}
kotlin {
    jvmToolchain(17)
}

compose.desktop {
    application {
        mainClass = "app.AppKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "trees-visualizer"
            packageVersion = "1.0.0"
            licenseFile.set(project.file("../LICENSE"))
            
            macOS {
                iconFile.set(project.file("src/main/resources/icon.icns"))
            }
            windows {
                iconFile.set(project.file("src/main/resources/icon.ico"))
            }
            linux {
                iconFile.set(project.file("src/main/resources/icon.png"))
            }
        }

    }
}
