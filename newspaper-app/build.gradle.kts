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

dependencies {
    implementation("org.zalando:problem-spring-web-starter:0.29.1")
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

    implementation("ru.clevertec:loggin-starter:${project.findProperty("loggingStarterVersion")}")
    implementation("ru.clevertec:exception-starter:${project.findProperty("exceptionStarterVersion")}")

    runtimeOnly("org.postgresql:postgresql")

    annotationProcessor("org.mapstruct:mapstruct-processor:${project.findProperty("mapstructVersion")}")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.boot:spring-boot-testcontainers")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:postgresql")
    testImplementation("org.springframework.security:spring-security-test:6.3.4")
    testImplementation("org.wiremock:wiremock-jetty12:3.9.2")

    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
