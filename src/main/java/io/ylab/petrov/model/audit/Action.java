package io.ylab.petrov.model.audit;


import io.ylab.petrov.model.user.User;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@ToString
@Builder
/**
 * Класс, описывающий действие пользователя в системе.
 */
public class Action {
    /**
     * Объект пользователя, выполнившего действие.
     */
    private User user;
    /**
     * Тип действия пользователя.
     */
    private Activity activity;
    /**
     * Дата и время действия
     */
    private LocalDateTime dateTime;
}
