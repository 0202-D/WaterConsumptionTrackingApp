package io.ylab.petrov.model.audit;


import io.ylab.petrov.model.user.User;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
/**
 * Класс, описывающий действие пользователя в системе.
 */
public class Action {
    /**
     * Объект пользователя, выполнившего действие.
     */
    private Long userId;
    /**
     * Тип действия пользователя.
     */
    private Activity activity;
    /**
     * Дата и время действия
     */
    private LocalDateTime dateTime;
}
