package ru.clevertec.exception.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProblemUtil {

    private ProblemUtil() {
    }

    public static ResourceNotFoundException resourceNotFound(String resourceName, Long id) {
        String format = "%s id: %s not found.".formatted(resourceName, id);
        log.warn(format);
        return new ResourceNotFoundException(format);
    }
}
