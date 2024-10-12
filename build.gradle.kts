import org.apache.tools.ant.filters.ReplaceTokens
import java.net.InetAddress
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val postgres_version: String by project
val hikaricp_version: String by project
val exposed_version: String by project

plugins {
    kotlin("jvm") version "1.9.23"
    id("io.ktor.plugin") version "2.3.9"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.23"
    id("com.github.johnrengelman.shadow")
}
tasks.shadowJar {
    manifest {
        attributes["Main-Class"] = "com.realtor.ApplicationKt"
    }
}
group = "com.example"
version = "0.0.1"

application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
tasks.processResources {
    from(sourceSets["main"].resources.srcDirs)
    into("$buildDir/upload/products")
    duplicatesStrategy = DuplicatesStrategy.INCLUDE

    filesMatching("application.conf") {
        filter(
            ReplaceTokens::class, "tokens" to mapOf(
                "BUILD_VERSION" to version,
                "BUILD_DATE" to DateTimeFormatter.ISO_DATE_TIME.format(ZonedDateTime.now()),
                "BUILD_MACHINE" to InetAddress.getLocalHost().hostName
            )
        )
    }
}
java {
    sourceCompatibility = JavaVersion.VERSION_20
    targetCompatibility = JavaVersion.VERSION_20
}

tasks.compileJava {
    options.compilerArgs.addAll(listOf("-source", "20", "-target", "20"))
}
repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-sessions-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("org.jetbrains.exposed:exposed-core:0.41.1")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")
    implementation("com.h2database:h2:2.2.220")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-default-headers-jvm")
    implementation("io.ktor:ktor-server-caching-headers-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    testImplementation("io.ktor:ktor-server-tests-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("com.zaxxer:HikariCP:$hikaricp_version")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposed_version")
    implementation("org.postgresql:postgresql:$postgres_version")
}
tasks.create("stage") {
    dependsOn("installDist")
}