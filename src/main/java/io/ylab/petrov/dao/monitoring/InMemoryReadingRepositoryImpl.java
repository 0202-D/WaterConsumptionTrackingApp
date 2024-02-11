package io.ylab.petrov.dao.monitoring;

import io.ylab.petrov.dto.monitoring.ReadingInMonthRqDto;
import io.ylab.petrov.dto.monitoring.ReadingRqDto;
import io.ylab.petrov.dto.monitoring.ReadingRsDto;
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
    public Optional<ReadingRsDto> getCurrentReading(ReadingRqDto dto) {
        Optional<Reading> reading = readings.stream()
                .filter(el -> el.getUser().getId() == dto.getUserId()
                        && el.getMeter().getId() == dto.getMeterId()
                        && el.isCurrent()).findFirst();
        return Optional.ofNullable(ReadingRsDto.builder()
                .date(reading.get()
                        .getDate())
                .reading(reading.get()
                        .getMeterReading())
                .build());
    }

    @Override
    public Optional<Reading> getReadingForMonth(ReadingInMonthRqDto rq) {
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
