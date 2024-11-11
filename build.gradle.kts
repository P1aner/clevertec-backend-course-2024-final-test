plugins {
    java
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    id("com.google.protobuf") version "0.9.4"
}

extra["springCloudVersion"] = "2023.0.3"
extra["springBootVersion"] = "3.3.5"
extra["lombokVersion"] = "1.18.34"
extra["mapstructVersion"] = "1.6.2"
extra["springDocOpenApi"] = "2.6.0"
extra["zalandoWebStarterAndViolations"] = "0.29.1"
extra["zalandoJacksonDataTypeAndProblem"] = "0.27.1"
extra["wiremockJetty12"] = "3.9.2"
extra["springSecurityTest"] = "6.3.4"
extra["protobufVersion"] = "4.28.2"

allprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }
}

subprojects {
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "com.google.protobuf")
    apply(plugin = "java")

    dependencies {
        compileOnly("org.projectlombok:lombok:${property("lombokVersion")}")
        annotationProcessor("org.projectlombok:lombok:${property("lombokVersion")}")
    }

    dependencyManagement {
        imports {
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
        }
    }

    java {
        toolchain {
            languageVersion = JavaLanguageVersion.of(21)
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

}