val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val exposed_version: String by project

plugins {
    kotlin("jvm") version "1.9.24"
    id("io.ktor.plugin") version "2.3.10"
}

group = "kipi"
version = "0.0.1"

application {
    mainClass.set("ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-jackson")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    implementation("net.spy:spymemcached:2.12.3")
    implementation("io.ktor:ktor-server-call-logging")

    implementation("io.ktor:ktor-server-host-common-jvm:2.3.7")
    implementation("io.ktor:ktor-server-status-pages-jvm:2.3.7")

    implementation("com.google.guava:guava:10.0.1")
    implementation("com.google.guava:guava:31.1-jre")

    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test:$kotlin_version")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.1.0")
    testImplementation("io.ktor:ktor-client-content-negotiation")
    testImplementation("org.mockito:mockito-inline:2.13.0")
    testImplementation("org.assertj:assertj-core:3.11.1")
}

sourceSets {
    main {
        java.srcDir("src")
        resources.srcDir("resources")
    }
    test {
        java.srcDir("test")
        resources.srcDir("test")
    }
}