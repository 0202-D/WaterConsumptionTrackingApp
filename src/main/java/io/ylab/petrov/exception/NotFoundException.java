package io.ylab.petrov.exception;

import org.springframework.lang.Nullable;
/**
 Класс для исключения, возникающего при попытке получить в бд не существующие данные
 */
public class NotFoundException extends IllegalArgumentException {

    public NotFoundException(@Nullable String message) {
        super(message);
    }
}
