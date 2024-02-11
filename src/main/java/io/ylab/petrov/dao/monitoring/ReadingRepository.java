package io.ylab.petrov.dao.monitoring;

import io.ylab.petrov.dto.monitoring.ReadingInMonthRqDto;
import io.ylab.petrov.dto.monitoring.ReadingRqDto;
import io.ylab.petrov.dto.monitoring.ReadingRsDto;
import io.ylab.petrov.model.readout.Reading;

import java.util.List;
import java.util.Optional;

public interface ReadingRepository {
    void addReading(Reading reading);

    Optional<ReadingRsDto> getCurrentReading(ReadingRqDto dto);

    Optional<Reading> getReadingForMonth(ReadingInMonthRqDto rq);

    List<Reading> historyReadingsByUserId(long id);

    void save(Reading reading);

}
