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
    implementation("org.zalando:problem-spring-web-starter:0.29.1")
    implementation("org.zalando:problem-violations:0.29.1")
    implementation("org.zalando:jackson-datatype-problem:0.27.1")
    implementation("org.zalando:problem:0.27.1")
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