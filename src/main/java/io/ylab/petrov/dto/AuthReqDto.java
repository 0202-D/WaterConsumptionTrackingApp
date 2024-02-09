package io.ylab.petrov.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder

public record AuthReqDto ( @NotBlank String userName,@NotBlank String password){

}
