package io.ylab.petrov.dao.monitoring;

import io.ylab.petrov.dto.ReadingInMonthRq;
import io.ylab.petrov.dto.ReadingRqDto;
import io.ylab.petrov.dto.ReadingRs;
import io.ylab.petrov.model.readout.Reading;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
public class InMemoryReadingRepositoryImpl implements ReadingRepository {
    private final List<Reading> readings = new ArrayList<>();

    @Override
    public void addReading(Reading reading) {
        readings.add(reading);
    }

    @Override
    public Optional<ReadingRs> getCurrentReading(ReadingRqDto dto) {
        Optional<Reading> reading = readings.stream()
                .filter(el -> el.getUser().getId() == dto.getUserId()
                        && el.getMeter().getId() == dto.getMeterId()
                        && el.isCurrent()).findFirst();
        return Optional.ofNullable(ReadingRs.builder()
                .date(reading.get()
                        .getDate())
                .reading(reading.get()
                        .getMeterReading())
                .build());
    }

    @Override
    public Optional<Reading> getReadingForMonth(ReadingInMonthRq rq) {
        return readings.stream()
                .filter(el -> el.getUser().getId() == rq.userId()
                        && el.getMeter().getId() == rq.meterId()
                        && el.getDate().getMonth() == rq.month()).findFirst();
    }

    @Override
    public List<Reading> historyReadingsByUserId(long id) {
        return readings.stream()
                .filter(el -> el.getUser().getId() == id)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Reading reading) {

    }
}
