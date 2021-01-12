plugins {
    val kotlinVersion = "1.4.21"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    idea
    id("de.fuerstenau.buildconfig") version "1.1.8"
}

group = "com.github.bekirev.aoc"
version = "1.0"

repositories {
    mavenCentral()
    jcenter()
}

buildConfig {
    buildConfigField("String", "COOKIE", project.findProperty("COOKIE")?.toString() ?: "Please provide cookie")
}

val kotlinCoroutinesVersion = "1.4.2"
val ktorVersion = "1.5.0"
val kotestVersion = "4.3.1"
val mockitoVersion = "3.6.28"
val kotlinMockitoVersion = "2.2.0"
val kotlinJdk8Version = "1.4.21-2"
val kotlinSerializationVersion = "1.0.1"

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinJdk8Version")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:$kotlinMockitoVersion")
    testImplementation(kotlin("test-junit5"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
    kotlinOptions.jvmTarget = "14"
}
