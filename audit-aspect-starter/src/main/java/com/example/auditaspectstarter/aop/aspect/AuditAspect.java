package com.example.auditaspectstarter.aop.aspect;


import com.example.auditaspectstarter.dao.ActionRepository;
import com.example.auditaspectstarter.model.*;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
/**
 * Класс аспекта для аудита действий, связанных с мониторингом операций сервиса.
 * Этот аспект регистрирует запрошенные действия, такие как getCurrentReading, getReadingForMonth,
 * historyReadingsByUserId и addReading.
 * Он захватывает идентификатор пользователя и тип выполненной операции вместе с отметкой времени.
 */
@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {
    private final ActionRepository actionRepository;
    /**
     * Регистрирует запрошенное действие для операции получения текущих показаний.
     * @param dto Объект запроса, содержащий идентификатор пользователя.
     */
    @After(value = "execution(* *..*ServiceImpl.getCurrentReading(..))&&args(dto)")
    public void addRequestedActivity(Object dto) {
        Action action = Action.builder()
                .activity(Activity.REQUESTED)
                .dateTime(LocalDateTime.now())
                .build();
        actionRepository.addAction(action);
    }
    /**
     * Регистрирует запрошенное действие для операции получения показания за месяц.
     * @param dto Объект запроса, содержащий идентификатор пользователя.
     */
    @After(value = "execution(* *..*ServiceImpl.getReadingForMonth(..))&&args(dto)")
    public void addRequestedForMonthActivity(Object dto) {
        Action action = Action.builder()
                .activity(Activity.REQUESTED)
                .dateTime(LocalDateTime.now())
                .build();
        actionRepository.addAction(action);
    }
    /**
     * Регистрирует запрошенное действие для операции просмотра истории
     * @param userId Идентификатор пользователя, для которого извлекается история.
     */
    @After(value = "execution(* *..*ServiceImpl.historyReadingsByUserId(long)) && args(userId)")
    public void addHistoryActivity(long userId) throws Throwable {
        actionRepository.addAction(Action.builder()
                .userId(userId)
                .activity(Activity.HISTORY)
                .dateTime(LocalDateTime.now())
                .build());
    }
    /**
     * Регистрирует запрошенное действие для операции добавления показаний.
     * @param dto Объект запроса, содержащий идентификатор пользователя.
     */
    @After(value = "execution(* *..*ServiceImpl.addReading(..))&&args(dto)")
    public void addActionActivity(Object dto) {
        Action action = Action.builder()
                .activity(Activity.SUBMITTED)
                .dateTime(LocalDateTime.now())
                .build();
        actionRepository.addAction(action);
    }
}
