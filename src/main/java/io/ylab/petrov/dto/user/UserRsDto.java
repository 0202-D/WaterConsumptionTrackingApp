package io.ylab.petrov.dto.user;

import io.ylab.petrov.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * dto ответа на регистрацию и аутентификацию пользователя
 */
public class UserRsDto {
    /**
     * идентификатор пользователя
     */
    private long userId;
    /**
     * имя пользователя
     */
    private String userName;
    /**
     * роль пользователя
     */
    private Role role;
}
