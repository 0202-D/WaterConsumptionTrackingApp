package io.ylab.petrov.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReadingRs {
    private BigDecimal reading;
    private LocalDate date;
}
