package io.ylab.petrov.dto.monitoring;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
