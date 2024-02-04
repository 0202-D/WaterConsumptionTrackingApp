package io.ylab.petrov.dao.monitoring;

import io.ylab.petrov.dto.ReadingInMonthRq;
import io.ylab.petrov.dto.ReadingRqDto;
import io.ylab.petrov.dto.ReadingRs;
import io.ylab.petrov.model.readout.Reading;

import java.util.List;
import java.util.Optional;

public interface ReadingRepository {
    void addReading(Reading reading);

    Optional<ReadingRs> getCurrentReading(ReadingRqDto dto);

    Optional<Reading> getReadingForMonth(ReadingInMonthRq rq);

    List<Reading> historyReadingsByUserId(long id);

    void save(Reading reading);

}
