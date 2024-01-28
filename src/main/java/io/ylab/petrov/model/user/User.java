package io.ylab.petrov.model.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private long id;
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
