package io.ylab.petrov.aop.aspect;

import io.ylab.petrov.dao.audit.ActionRepository;
import io.ylab.petrov.dao.audit.JdbcActionRepository;
import io.ylab.petrov.dto.monitoring.AddReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingInMonthRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingRequestDto;
import io.ylab.petrov.model.audit.Action;
import io.ylab.petrov.model.audit.Activity;
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
public class AuditAspect {
    private final ActionRepository actionRepository = new JdbcActionRepository();
    /**
     * Регистрирует запрошенное действие для операции получения текущих показаний.
     * @param dto Объект запроса, содержащий идентификатор пользователя.
     */
    @After(value = "execution(* *..*ServiceImpl.getCurrentReading(..))&&args(dto)")
    public void addRequestedActivity(Object dto) {
        ReadingRequestDto req = (ReadingRequestDto) dto;

        Action action = Action.builder()
                .userId(req.getUserId())
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
        ReadingInMonthRequestDto req = (ReadingInMonthRequestDto) dto;
        Action action = Action.builder()
                .userId(req.userId())
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
        AddReadingRequestDto addReadingRequestDto = (AddReadingRequestDto) dto;
        Action action = Action.builder()
                .userId(addReadingRequestDto.getUserId())
                .activity(Activity.SUBMITTED)
                .dateTime(LocalDateTime.now())
                .build();
        actionRepository.addAction(action);
    }
}
