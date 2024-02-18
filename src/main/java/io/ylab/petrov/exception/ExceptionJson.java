package io.ylab.petrov.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Класс ExceptionJson представляет объект ответа с информацией об исключении.
 * Содержит сообщение об ошибке и код HTTP ответа.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExceptionJson {
   private String message;
   private int httpResponse;

}
