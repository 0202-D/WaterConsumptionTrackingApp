package io.ylab.petrov.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ResponseStatus;
/**
 Класс для исключения, возникающего при передаче некорректных данных.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, value = HttpStatus.BAD_REQUEST)
public class IncorrectDataException extends IllegalArgumentException {
    public IncorrectDataException(@Nullable String message) {
        super(message);
    }
}
