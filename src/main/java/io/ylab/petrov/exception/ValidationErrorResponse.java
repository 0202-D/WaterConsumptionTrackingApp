package io.ylab.petrov.exception;

import java.util.List;
/**
 Класс, представляющий объект ответа об ошибке валидации.
 @param violations список нарушений валидации
 */
public record ValidationErrorResponse(List<Violation> violations) {
}
