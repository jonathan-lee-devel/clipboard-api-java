group = "io.jonathanlee"
version = "1.0-SNAPSHOT"

plugins {
    application
    id("jacoco-report-aggregation")
    id("org.springframework.boot") version "3.0.1"
    id("io.spring.dependency-management") version "1.1.0"
}

repositories {
    mavenCentral()
}

dependencies {
    jacocoAggregation(project(":registration-service"))
}

tasks.check {
    dependsOn(tasks.named<JacocoReport>("testCodeCoverageReport"))
}