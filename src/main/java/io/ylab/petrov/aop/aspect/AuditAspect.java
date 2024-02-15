package io.ylab.petrov.aop.aspect;

import io.ylab.petrov.dao.audit.ActionRepository;
import io.ylab.petrov.dao.audit.JdbcActionRepository;
import io.ylab.petrov.dao.user.JdbcUserRepository;
import io.ylab.petrov.dao.user.UserRepository;
import io.ylab.petrov.dto.monitoring.AddReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingInMonthRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingRequestDto;
import io.ylab.petrov.exception.NotFoundException;
import io.ylab.petrov.model.audit.Action;
import io.ylab.petrov.model.audit.Activity;
import io.ylab.petrov.model.user.User;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

import java.time.LocalDateTime;

@Aspect
public class AuditAspect {
    private UserRepository userRepository = new JdbcUserRepository();
    private ActionRepository actionRepository = new JdbcActionRepository();

    @After(value = "execution(* *..*ServiceImpl.getCurrentReading(..))&&args(dto)")
    public void addRequestedActivity(Object dto) {
        ReadingRequestDto req = (ReadingRequestDto) dto;
        User user = userRepository.getUserById(req.getUserId())
                .orElseThrow(() -> new NotFoundException("Пользователя с таким id не существует"));
        Action action = Action.builder()
                .user(user)
                .activity(Activity.REQUESTED)
                .dateTime(LocalDateTime.now())
                .build();
        actionRepository.addAction(action);
    }

    @After(value = "execution(* *..*ServiceImpl.getReadingForMonth(..))&&args(dto)")
    public void addRequestedForMonthActivity(Object dto) {
        ReadingInMonthRequestDto req = (ReadingInMonthRequestDto)dto;
            User user = userRepository.getUserById(((ReadingInMonthRequestDto) dto).userId())
                    .orElseThrow(() -> new NotFoundException("Пользователя с таким id не существует"));
            Action action = Action.builder()
                    .user(user)
                    .activity(Activity.REQUESTED)
                    .dateTime(LocalDateTime.now())
                    .build();
            actionRepository.addAction(action);
    }

    @After(value = "execution(* *..*ServiceImpl.historyReadingsByUserId(long)) && args(userId)")
    public void addHistoryActivity(long userId) throws Throwable {
        var user = userRepository.getUserById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователя с таким id не существует"));
        actionRepository.addAction(Action.builder()
                .user(user)
                .activity(Activity.HISTORY)
                .dateTime(LocalDateTime.now())
                .build());
    }

    @After(value = "execution(* *..*ServiceImpl.addReading(..))&&args(dto)")
    public void addActionActivity(Object dto) {
        AddReadingRequestDto addReadingRequestDto = (AddReadingRequestDto)dto;
        User user = userRepository.getUserById(((AddReadingRequestDto) dto).userId())
                .orElseThrow(() -> new RuntimeException("Пользователя с таким id не существует"));
        Action action = Action.builder()
                .user(user)
                .activity(Activity.SUBMITTED)
                .dateTime(LocalDateTime.now())
                .build();
        actionRepository.addAction(action);
    }
}
