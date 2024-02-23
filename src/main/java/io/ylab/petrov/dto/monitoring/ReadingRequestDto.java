package io.ylab.petrov.dto.monitoring;

import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private long userId;
    @NotNull
    private long meterId;
}
