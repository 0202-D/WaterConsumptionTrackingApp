package io.ylab.petrov.model.readout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/**
 * Класс, представляющий сущность "Счетчик"
 * Данный класс обеспечивает доступ к информации о счетчике, включая его идентификатор и имя.
 */
public class Meter {
    /**
     * Уникальный идентификатор счетчика
     */
    private Long id;
    /**
     * Тип счетчика
     */
    private String name;
}
