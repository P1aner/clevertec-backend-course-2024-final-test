package ru.clevertec.newspaper.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProblemUtil {

    public static ResourceNotFoundException newsNotFound(Long id) {
        String format = "News id: %s not found.".formatted(id);
        log.warn(format);
        return new ResourceNotFoundException(format);
    }

    public static ResourceNotFoundException commentNotFound(Long id) {
        String format = "Comment id: %s not found.".formatted(id);
        log.warn(format);
        return new ResourceNotFoundException(format);
    }

}
