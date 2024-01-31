package io.ylab.petrov.dto;

import lombok.Builder;

import java.math.BigDecimal;
@Builder
public record AddReadingRqDto(long userId, BigDecimal readout, long meterId) {
}
