group = "ru.clevertec"
version = "0.0.1"

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation("org.zalando:problem-spring-web-starter:${property("zalandoWebStarterAndViolations")}")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.liquibase:liquibase-core")
    implementation("org.mapstruct:mapstruct:${project.findProperty("mapstructVersion")}")
    implementation("com.h2database:h2")
    implementation("ru.clevertec:exception-starter:${project.findProperty("exceptionStarterVersion")}")
    compileOnly("org.projectlombok:lombok")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor("org.mapstruct:mapstruct-processor:${project.findProperty("mapstructVersion")}")
}

