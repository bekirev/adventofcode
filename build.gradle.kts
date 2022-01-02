plugins {
    val kotlinVersion = "1.6.10"
    kotlin("jvm") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    idea
//    id("de.fuerstenau.buildconfig") version "1.1.8"
}

group = "com.github.bekirev.aoc"
version = "1.0"

repositories {
    mavenCentral()
}

//buildConfig {
//    buildConfigField("String", "COOKIE", project.findProperty("COOKIE")?.toString() ?: "Please provide cookie")
//}

val kotlinCoroutinesVersion = "1.6.0"
val ktorVersion = "1.6.7"
val kotestVersion = "5.0.2"
val mockitoVersion = "4.2.0"
val kotlinMockitoVersion = "2.2.0"
val mockkVersion = "1.12.2"
val kotlinSerializationVersion = "1.3.2"

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinCoroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinCoroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinSerializationVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("io.mockk:mockk:${mockkVersion}")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:$kotlinMockitoVersion")
    testImplementation(kotlin("test-junit5"))
}

tasks.test {
    useJUnitPlatform()
}

//tasks.compileKotlin {
//    jvmTarget = "17"
//}

//tasks.withType<KotlinCompile> {
//    kotlinOptions {
//        jvmTarget = "17"
//    }
//}
