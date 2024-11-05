package ru.clevertec.newspaper.config;

import jakarta.validation.constraints.Min;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import ru.clevertec.cache.CacheAlgorithm;

@Validated
@ConfigurationProperties(prefix = "app.cache")
public record CacheProperties(

    @Min(0)
    int maxSize,

    @NonNull
    CacheAlgorithm algorithm

) {
}
