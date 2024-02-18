package io.ylab.petrov.exception;

import java.util.List;

public record ValidationErrorResponse(List<Violation> violations) {
}
