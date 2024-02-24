package com.example.auditaspectstarter.model;

import lombok.Builder;

import java.time.Month;

/**
 * dto запроса на просмотр показаний за месяц
 */
@Builder
public record ReadingInMonthRequestDto(long userId, long meterId,Month month) {

}
