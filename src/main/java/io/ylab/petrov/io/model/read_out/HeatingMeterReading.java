package io.ylab.petrov.io.model.read_out;

import io.ylab.petrov.io.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeatingMeterReading {
    private User user;
    private BigDecimal readOut;
    private LocalDate localDate;

}
