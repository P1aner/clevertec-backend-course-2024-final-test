package ru.clevertec.newspaper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.clevertec.newspaper.config.CacheProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = {CacheProperties.class})
public class NewspaperApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewspaperApplication.class, args);
    }

}
