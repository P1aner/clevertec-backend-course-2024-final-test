plugins {
    id("maven-publish")
}

group = "ru.clevertec"
version = project.findProperty("exceptionStarterVersion") as String

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation("org.zalando:problem-spring-web-starter:${property("zalandoWebStarterAndViolations")}")
    implementation("org.zalando:problem-violations:${property("zalandoWebStarterAndViolations")}")
    implementation("org.zalando:jackson-datatype-problem:${property("zalandoJacksonDataTypeAndProblem")}")
    implementation("org.zalando:problem:${property("zalandoJacksonDataTypeAndProblem")}")
    implementation("org.springframework.boot:spring-boot-starter-web:${property("springBootVersion")}")
    implementation("org.springframework.boot:spring-boot-starter:${property("springBootVersion")}")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}