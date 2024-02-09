package io.ylab.petrov.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReadingRqDto {
    @NotBlank private long userId;
    @NotBlank private long meterId;
}
