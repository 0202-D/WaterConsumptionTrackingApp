package io.ylab.petrov.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

import java.time.Month;

@Builder
public record ReadingInMonthRq(@NotBlank long userId, @NotBlank long meterId, @NotBlank Month month) {

}
