package io.ylab.petrov.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * dto запроса на регистрацию и аутентификацию пользователя
 */
public class UserRequestDto {
    /**
     * имя пользователя
     */
    @NotNull
    private String userName;
    /**
     * пароль пользователя
     */
    @NotBlank
    private String password;
}
