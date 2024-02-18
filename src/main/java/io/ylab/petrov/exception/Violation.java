package io.ylab.petrov.exception;
/**
 Класс представляет собой объект нарушения с заданными именем поля и сообщением.
 */
public record Violation(String fieldName, String message) {
}
