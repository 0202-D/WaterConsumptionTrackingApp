package com.example.auditaspectstarter.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
