package io.ylab.petrov.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * Класс, представляющий пользователя системы.
 */
public class User {
    /**
     * Уникальный идентификатор
     */
    private Long id;
    /**
     * Имя пользователя
     */
    private String userName;
    /**
     * Пароль пользователя
     */
    private String password;
    /**
     * Роль пользователя
     */
    private Role role;
}
