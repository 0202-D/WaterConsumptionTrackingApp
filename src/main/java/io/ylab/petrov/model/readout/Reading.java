package io.ylab.petrov.model.readout;

import io.ylab.petrov.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * Класс, представляющий сущность Показания
 * Данный класс обеспечивает доступ к информации о показании, включая его идентификатор
 * пользователя подавшего показания
 * показание прибора
 * счетчик показания которого переданы
 * дата передачи показаний
 * флаг помечающий показание текущим
 */
public class Reading {
    /**
     * Уникальный идентификатор счетчика
     */
    private Long id;
    /**
     * Пользователь
     */
    private User user;
    /**
     * Показания
     */
    private BigDecimal meterReading;
    /**
     * Счетчик
     */
    private Meter meter;
    /**
     * Дата
     */
    private LocalDate date;
    /**
     * Флаг является ли показание текущим
     */
    private boolean isCurrent;

}
