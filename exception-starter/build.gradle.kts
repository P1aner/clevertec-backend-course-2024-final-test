plugins {
    id("java-library")
    id("maven-publish")
}

group = "ru.clevertec"
version = "0.0.1"

val springBootVersion = "3.3.4"
val lombokVersion = "1.18.34"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("org.zalando:problem-spring-web-starter:0.29.1")
    implementation("org.zalando:jackson-datatype-problem:0.27.1")
    implementation("org.zalando:problem:0.27.1")
    implementation("org.zalando:problem-violations:0.29.1")
    implementation("org.springframework.boot:spring-boot-starter-web:${springBootVersion}")
    implementation("org.springframework.boot:spring-boot-starter:${springBootVersion}")
    compileOnly("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}