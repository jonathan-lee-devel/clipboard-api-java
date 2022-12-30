group = "io.jonathanlee"
version = "1.0-SNAPSHOT"

plugins {
    application
    id("jacoco-report-aggregation")
    id("org.sonarqube") version "3.5.0.2730"
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

sonar {
    properties {
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.organization", "jonathan-lee-devel")
        property("sonar.projectKey", "jonathan-lee-devel_clipboard-api")
    }
}