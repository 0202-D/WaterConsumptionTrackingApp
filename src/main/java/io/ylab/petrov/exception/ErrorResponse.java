package io.ylab.petrov.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/**
 * Класс ErrorResponse представляет объект ответа с информацией об ошибке.
 * Содержит код ошибки и сообщение об ошибке.
 */
@Getter
@Setter
@Builder
public class ErrorResponse {
    private ErrorCodes code;
    private String message;
}
