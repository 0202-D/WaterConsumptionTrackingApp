package io.ylab.petrov.dto.monitoring;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.Month;
/**
 * dto запроса на просмотр показаний за месяц
 */
@Builder
public record ReadingInMonthRqDto(@NotBlank long userId, @NotBlank long meterId, @NotBlank Month month) {

}
