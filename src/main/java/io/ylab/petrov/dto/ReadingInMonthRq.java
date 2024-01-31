package io.ylab.petrov.dto;

import lombok.Builder;

import java.time.Month;

@Builder
public record ReadingInMonthRq(long userId, long meterId, Month month) {

}
