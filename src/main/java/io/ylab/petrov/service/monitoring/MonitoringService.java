package io.ylab.petrov.service.monitoring;

import io.ylab.petrov.dto.monitoring.AddReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingInMonthRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingResponseDto;
import io.ylab.petrov.model.readout.Reading;

import java.util.List;
import java.util.Optional;

public interface MonitoringService {
    boolean addReading(AddReadingRequestDto dto);

    Optional<ReadingResponseDto> getCurrentReading(ReadingRequestDto dto);

    Optional<Reading> getReadingForMonth(ReadingInMonthRequestDto rq);

    List<Reading> historyReadingsByUserId(long userId);
}