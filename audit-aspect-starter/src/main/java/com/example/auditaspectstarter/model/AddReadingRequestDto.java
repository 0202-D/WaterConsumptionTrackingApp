package com.example.auditaspectstarter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * dto запроса на добавление показаний
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddReadingRequestDto {
    private Long userId;
    private BigDecimal readout;
    private Long meterId;
}
