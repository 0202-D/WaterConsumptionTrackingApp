package io.ylab.petrov.exception;

import org.springframework.http.HttpStatus;

/**
 * возвращаемые коды ошибок на фронт
 */
public enum ErrorCode {


    ERR_UNEXPECTED(HttpStatus.INTERNAL_SERVER_ERROR),

    ERR_NOT_FOUND(HttpStatus.NOT_FOUND),

    ERR_INCORRECT_DATA(HttpStatus.BAD_REQUEST);



    private final HttpStatus httpStatus;

    ErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
