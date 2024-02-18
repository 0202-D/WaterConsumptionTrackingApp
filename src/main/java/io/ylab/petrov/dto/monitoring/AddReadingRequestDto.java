package io.ylab.petrov.dto.monitoring;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * dto запроса на добавления показаний
 */
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
/**
 * dto запроса на добавление показаний
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddReadingRequestDto {
    @NotNull
    private Long userId;
    @NotNull
    private BigDecimal readout;
    @NotNull
    private Long meterId;
}
