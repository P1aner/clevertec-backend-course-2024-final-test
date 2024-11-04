package ru.clevertec.newspaper.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ru.clevertec.cache.Cache;
import ru.clevertec.cache.LFUCache;
import ru.clevertec.cache.LRUCache;

@Configuration
@Profile("!redis")
public class CacheConfiguration {

    @Autowired
    private CacheProperties cacheProperties;

    @Bean
    public Cache<String, Object> cache() {
        return switch (cacheProperties.algorithm()) {
            case LFU -> new LFUCache<>(cacheProperties.maxSize());
            case LRU -> new LRUCache<>(cacheProperties.maxSize());
        };
    }
}
