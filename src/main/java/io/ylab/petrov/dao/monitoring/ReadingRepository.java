package io.ylab.petrov.dao.monitoring;

import io.ylab.petrov.dto.monitoring.ReadingInMonthRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingResponseDto;
import io.ylab.petrov.model.readout.Reading;

import java.util.List;
import java.util.Optional;

public interface ReadingRepository {
    void addReading(Reading reading);

    Optional<ReadingResponseDto> getCurrentReading(ReadingRequestDto dto);

    Optional<Reading> getReadingForMonth(ReadingInMonthRequestDto rq);

    List<Reading> historyReadingsByUserId(long id);

    void save(Reading reading);

}
