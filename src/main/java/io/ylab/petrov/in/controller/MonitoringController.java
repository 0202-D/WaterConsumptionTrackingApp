package io.ylab.petrov.in.controller;

import io.ylab.petrov.dto.monitoring.AddReadingRqDto;
import io.ylab.petrov.dto.monitoring.ReadingInMonthRqDto;
import io.ylab.petrov.dto.monitoring.ReadingRqDto;
import io.ylab.petrov.dto.monitoring.ReadingRsDto;
import io.ylab.petrov.model.readout.Reading;
import io.ylab.petrov.service.monitoring.MonitoringService;
import io.ylab.petrov.service.monitoring.MonitoringServiceImpl;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class MonitoringController {
    private final MonitoringService monitoringService = new MonitoringServiceImpl();

    public boolean addReading(AddReadingRqDto dto) {
        return monitoringService.addReading(dto);
    }

    public ReadingRsDto getCurrentReading(ReadingRqDto dto) {
        Optional<ReadingRsDto> reading = monitoringService.getCurrentReading(dto);
        return reading.orElse(null);
    }

    public Optional<Reading> getReadingForMonth(ReadingInMonthRqDto rq) {
        return monitoringService.getReadingForMonth(rq);
    }

    public List<Reading>historyReadingsByUserId(long userId){
        return monitoringService.historyReadingsByUserId(userId);
    }
}
