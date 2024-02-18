package io.ylab.petrov.model.audit;

import org.springframework.stereotype.Component;

/**
 * Перечисления активности.
 */

public enum Activity {
    /**
     * Пользователь зашел
     */
    ENTERED,
    /**
     * Пользователь вышел
     */
    EXITED,
    /**
     * Пользователь подал показания
     */
    SUBMITTED,
    /**
     * Пользователь посмотрел историю подачи показаний
     */
    HISTORY,
    /**
     * Пользователь запросил показания
     */
    REQUESTED,
    /**
     * Пользователь зарегестрировался
     */
    REGISTERED
}
