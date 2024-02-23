package io.ylab.petrov.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



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
