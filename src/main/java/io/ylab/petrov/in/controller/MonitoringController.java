package io.ylab.petrov.in.controller;

import io.ylab.petrov.dto.monitoring.AddReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingInMonthRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingResponseDto;
import io.ylab.petrov.model.readout.Reading;
import io.ylab.petrov.service.monitoring.MonitoringService;
import io.ylab.petrov.service.monitoring.MonitoringServiceImpl;
import lombok.Data;

import java.util.List;
import java.util.Optional;

@Data
public class MonitoringController {
    private final MonitoringService monitoringService = new MonitoringServiceImpl();

    public boolean addReading(AddReadingRequestDto dto) {
        return monitoringService.addReading(dto);
    }

    public ReadingResponseDto getCurrentReading(ReadingRequestDto dto) {
        Optional<ReadingResponseDto> reading = monitoringService.getCurrentReading(dto);
        return reading.orElse(null);
    }

    public Optional<Reading> getReadingForMonth(ReadingInMonthRequestDto rq) {
        return monitoringService.getReadingForMonth(rq);
    }

    public List<Reading>historyReadingsByUserId(long userId){
        return monitoringService.historyReadingsByUserId(userId);
    }
}
