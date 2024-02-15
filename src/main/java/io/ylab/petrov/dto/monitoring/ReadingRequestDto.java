package io.ylab.petrov.dto.monitoring;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank private long userId;
    @NotBlank private long meterId;
}
