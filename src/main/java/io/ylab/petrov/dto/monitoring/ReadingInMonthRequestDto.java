package io.ylab.petrov.dto.monitoring;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.Month;
/**
 * dto запроса на просмотр показаний за месяц
 */
@Builder
public record ReadingInMonthRequestDto(@NotNull long userId, @NotNull long meterId, @NotNull Month month) {

}
