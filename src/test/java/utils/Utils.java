package utils;

import io.ylab.petrov.dto.monitoring.AddReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingInMonthRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingResponseDto;
import io.ylab.petrov.dto.user.UserRequestDto;
import io.ylab.petrov.dto.user.UserResponseDto;
import io.ylab.petrov.model.audit.Action;
import io.ylab.petrov.model.audit.Activity;
import io.ylab.petrov.model.readout.Meter;
import io.ylab.petrov.model.readout.Reading;
import io.ylab.petrov.model.user.Role;
import io.ylab.petrov.model.user.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

public class Utils {
    public static User getUser() {
        return User.builder()
                .id(1L)
                .userName("testUser")
                .password("testPassword")
                .role(Role.USER)
                .build();
    }

    public static User getSecondUser() {
        return User.builder()
                .id(1L)
                .userName("testUser2")
                .password("testPassword2")
                .role(Role.USER)
                .build();
    }

    public static Action getAction() {
        return Action.builder()
                .userId(1L)
                .activity(Activity.ENTERED)
                .dateTime(LocalDateTime.now())
                .build();
    }

    public static Action getActionExited() {
        return Action.builder()
                .userId(2L)
                .activity(Activity.EXITED)
                .dateTime(LocalDateTime.now())
                .build();
    }

    public static UserRequestDto getUserRequestDto() {
        return UserRequestDto.builder()
                .userName("user")
                .password("user")
                .build();
    }

    public static UserResponseDto getUserResponseDto() {
        return UserResponseDto.builder()
                .userId(1L)
                .userName("user")
                .role(Role.USER).build();
    }

    public static Reading getReading() {
        return Reading.builder()
                .id(1L)
                .date(LocalDate.now())
                .meterReading(new BigDecimal(1000))
                .isCurrent(true)
                .user(getUser())
                .build();
    }

    public static ReadingInMonthRequestDto getReadingInMonthRequest() {
        return ReadingInMonthRequestDto.builder()
                .userId(1L)
                .meterId(1L)
                .month(Month.FEBRUARY)
                .build();
    }

    public static ReadingResponseDto getReadingResponseDto() {
        return ReadingResponseDto.builder()
                .reading(new BigDecimal(100))
                .date(LocalDate.now())
                .build();
    }

    public static Meter getMeter() {
        return Meter.builder()
                .id(1L)
                .name("HOT_WATER")
                .build();
    }

    public static AddReadingRequestDto getAddReadingRequestDto() {
        return AddReadingRequestDto.builder()
                .meterId(1L)
                .userId(1L)
                .readout(new BigDecimal(100500))
                .build();
    }

    public static ReadingRequestDto getReadingRequestDto() {
        return ReadingRequestDto.builder()
                .userId(1L)
                .meterId(1L)
                .build();
    }
}
