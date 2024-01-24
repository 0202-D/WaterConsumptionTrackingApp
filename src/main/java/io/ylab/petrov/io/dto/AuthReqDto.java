package io.ylab.petrov.io.dto;

import lombok.Builder;

@Builder
public record AuthReqDto(String userName, String password) {
}
