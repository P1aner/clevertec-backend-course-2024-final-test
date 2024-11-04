package ru.clevertec.exception.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ResourceNotFoundException extends AbstractThrowableProblem {
    public ResourceNotFoundException(String detail) {
        super(null, Status.NOT_FOUND.getReasonPhrase(), Status.NOT_FOUND, detail);
    }
}
