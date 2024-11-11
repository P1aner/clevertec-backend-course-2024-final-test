group = "ru.clevertec"
version = "0.0.1"

tasks.javadoc {
    setDestinationDir(file("build/docs/javadoс"))
    include("ru/clevertec/**")
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${property("protobufVersion")}"
    }
}

sourceSets {
    main {
        proto {
            srcDir("src/main/proto")
        }
        java {
            srcDir { "src/main/java" }
        }
    }
}

dependencies {
    implementation("com.google.protobuf:protobuf-java:${property("protobufVersion")}")
    implementation("com.google.protobuf:protobuf-java-util:${property("protobufVersion")}")
    implementation("org.zalando:problem-spring-web-starter:${property("zalandoWebStarterAndViolations")}")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.mapstruct:mapstruct:${project.findProperty("mapstructVersion")}")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${project.findProperty("springDocOpenApi")}")
    implementation("org.liquibase:liquibase-core")
    implementation("com.h2database:h2")

    implementation("ru.clevertec:log-starter:${project.findProperty("logStarterVersion")}")
    implementation("ru.clevertec:exception-starter:${project.findProperty("exceptionStarterVersion")}")

    runtimeOnly("org.postgresql:postgresql")

    annotationProcessor("org.mapstruct:mapstruct-processor:${project.findProperty("mapstructVersion")}")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.springframework.security:spring-security-test:${project.findProperty("springSecurityTest")}")
    testImplementation("org.wiremock:wiremock-jetty12:${project.findProperty("wiremockJetty12")}")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named<Copy>("processResources") {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}
