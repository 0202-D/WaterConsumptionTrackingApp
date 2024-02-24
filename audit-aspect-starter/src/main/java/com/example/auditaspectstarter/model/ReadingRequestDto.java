package com.example.auditaspectstarter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * dto запроса на просмотр текущих показаний
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadingRequestDto {
    private long userId;
    private long meterId;
}
