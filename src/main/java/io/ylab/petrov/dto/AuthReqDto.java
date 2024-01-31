package io.ylab.petrov.dto;

import lombok.Builder;

@Builder
public record AuthReqDto(String userName, String password) {
}
