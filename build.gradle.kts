plugins {
    val kotlinVersion = "2.1.20"
    id("com.android.library") version "8.7.2"
    kotlin("android") version kotlinVersion
    id("maven-publish")
    kotlin("plugin.serialization") version kotlinVersion
}

repositories {
    mavenCentral()
    google()
    mavenLocal()
    maven("https://jitpack.io")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

group = "com.github.hnau256"
version = "1.0.12"

android {
    namespace = "com.github.hnau256." + project.name.replace('-', '.')
    compileSdk = 35
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    publishing {
        singleVariant("release") {}
    }
}

dependencies {

    implementation("com.github.hnau256:common-kotlin:1.0.3")

    val arrow = "1.2.4"
    implementation("io.arrow-kt:arrow-core:$arrow")
    implementation("io.arrow-kt:arrow-core-serialization:$arrow")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")
    implementation("org.jetbrains.kotlinx:kotlinx-io-core:0.7.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
}

tasks {
    create<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(android.sourceSets["main"].java.srcDirs)
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group as String
            artifactId = project.name
            version = project.version as String
            afterEvaluate {
                from(components["release"])
            }
            artifact(tasks["sourcesJar"])
        }
    }
}