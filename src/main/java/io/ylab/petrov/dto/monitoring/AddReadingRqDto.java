package io.ylab.petrov.dto.monitoring;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
/**
 * dto запроса на добавления показаний
 */
import java.math.BigDecimal;
@Builder
public record AddReadingRqDto(@NotBlank long userId, @NotBlank BigDecimal readout, @NotBlank long meterId) {
}
