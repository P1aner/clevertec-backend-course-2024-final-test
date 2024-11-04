package ru.clevertec.loggin.config;

import org.springframework.context.annotation.Bean;
import ru.clevertec.loggin.aspect.LoggingAspect;

public class LogConfiguration {

    @Bean
    public LoggingAspect loggingAspect() {
        return new LoggingAspect();
    }
}
