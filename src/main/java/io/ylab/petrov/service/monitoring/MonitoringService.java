package io.ylab.petrov.service.monitoring;

import io.ylab.petrov.dto.monitoring.AddReadingRqDto;
import io.ylab.petrov.dto.monitoring.ReadingInMonthRqDto;
import io.ylab.petrov.dto.monitoring.ReadingRqDto;
import io.ylab.petrov.dto.monitoring.ReadingRsDto;
import io.ylab.petrov.model.readout.Reading;

import java.util.List;
import java.util.Optional;

public interface MonitoringService {
    boolean addReading(AddReadingRqDto dto);

    Optional<ReadingRsDto> getCurrentReading(ReadingRqDto dto);

    Optional<Reading> getReadingForMonth(ReadingInMonthRqDto rq);

    List<Reading> historyReadingsByUserId(long userId);
}