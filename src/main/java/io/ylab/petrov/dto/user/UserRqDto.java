package io.ylab.petrov.dto.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * dto запроса на регистрацию и аутентификацию пользователя
 */
public class UserRqDto {
    /**
     * имя пользователя
     */
    @NotBlank
    private String userName;
    /**
     * пароль пользователя
     */
    @NotBlank
    private String password;
}
