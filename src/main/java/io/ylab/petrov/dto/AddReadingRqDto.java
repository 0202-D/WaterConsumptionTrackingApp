package io.ylab.petrov.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record AddReadingRqDto(@NotBlank long userId, @NotBlank BigDecimal readout, @NotBlank long meterId) {
}
