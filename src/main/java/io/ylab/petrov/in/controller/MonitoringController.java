package io.ylab.petrov.in.controller;

import io.ylab.petrov.dto.AddReadingRqDto;
import io.ylab.petrov.dto.ReadingInMonthRq;
import io.ylab.petrov.dto.ReadingRqDto;
import io.ylab.petrov.dto.ReadingRs;
import io.ylab.petrov.model.readout.Reading;
import io.ylab.petrov.service.monitoring.MonitoringService;
import io.ylab.petrov.service.monitoring.MonitoringServiceImpl;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class MonitoringController {
    private final MonitoringService monitoringService = new MonitoringServiceImpl();

    public void addReading(AddReadingRqDto dto) {
        monitoringService.addReading(dto);
    }

    public ReadingRs getCurrentReading(ReadingRqDto dto) {
        Optional<ReadingRs> reading = monitoringService.getCurrentReading(dto);
        return reading.orElse(null);
    }

    public Optional<Reading> getReadingForMonth(ReadingInMonthRq rq) {
        return monitoringService.getReadingForMonth(rq);
    }

    public List<Reading>historyReadingsByUserId(long userId){
        return monitoringService.historyReadingsByUserId(userId);
    }
}
