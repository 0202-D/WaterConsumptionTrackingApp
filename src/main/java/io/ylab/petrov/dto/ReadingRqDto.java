package io.ylab.petrov.dto;

import lombok.Builder;

@Builder
public record ReadingRqDto(long userId, long meterId) {
}
