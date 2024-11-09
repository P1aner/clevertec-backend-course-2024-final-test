group = "ru.clevertec"
version = project.findProperty("cloudConfigVersion") as String

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation("org.springframework.cloud:spring-cloud-config-server")
}