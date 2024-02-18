package io.ylab.petrov.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 Класс для возвращения ответа
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Response {

    private String message;
}
