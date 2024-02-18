package io.ylab.petrov.dto.monitoring;

import lombok.Builder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Month;
/**
 * dto запроса на просмотр показаний за месяц
 */
@Builder
public record ReadingInMonthRequestDto(@NotNull long userId, @NotNull long meterId, @NotNull Month month) {

}
