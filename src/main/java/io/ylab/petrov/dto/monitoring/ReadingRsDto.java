package io.ylab.petrov.dto.monitoring;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
/**
 * dto ответа на запросмотр показаний
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadingRsDto {
    private BigDecimal reading;
    private LocalDate date;
}
