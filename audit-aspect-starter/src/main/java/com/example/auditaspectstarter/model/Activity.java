package com.example.auditaspectstarter.model;

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
