package io.ylab.petrov.service.monitoring;

import io.ylab.petrov.dto.AddReadingRqDto;
import io.ylab.petrov.dto.ReadingInMonthRq;
import io.ylab.petrov.dto.ReadingRqDto;
import io.ylab.petrov.dto.ReadingRs;
import io.ylab.petrov.model.readout.Reading;

import java.util.List;
import java.util.Optional;

public interface MonitoringService {
    void addReading(AddReadingRqDto dto);

    Optional<ReadingRs> getCurrentReading(ReadingRqDto dto);

    Optional<Reading> getReadingForMonth(ReadingInMonthRq rq);

    List<Reading> historyReadingsByUserId(long userId);
}