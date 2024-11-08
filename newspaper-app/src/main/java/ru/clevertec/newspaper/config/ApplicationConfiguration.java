package ru.clevertec.newspaper.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(value = {CacheProperties.class})
@EnableFeignClients
public class ApplicationConfiguration {
}
