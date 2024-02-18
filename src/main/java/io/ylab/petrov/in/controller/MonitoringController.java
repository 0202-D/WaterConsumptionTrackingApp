package io.ylab.petrov.in.controller;

import io.ylab.petrov.dto.monitoring.AddReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingInMonthRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingResponseDto;
import io.ylab.petrov.model.readout.Reading;
import io.ylab.petrov.service.monitoring.MonitoringService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Data
@RequiredArgsConstructor
@RestController
public class MonitoringController {
    private final MonitoringService monitoringService;

    @PostMapping("/add")
    public ResponseEntity addReading(@RequestBody @Valid AddReadingRequestDto dto) {
        monitoringService.addReading(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/getcurrent")
    public ReadingResponseDto getCurrentReading(@RequestBody @Valid ReadingRequestDto dto) {
        Optional<ReadingResponseDto> reading = monitoringService.getCurrentReading(dto);
        return reading.orElse(null);
    }

    @PostMapping("/getbymonth")
    public Optional<Reading> getReadingForMonth(@RequestBody @Valid ReadingInMonthRequestDto rq) {
        return monitoringService.getReadingForMonth(rq);
    }

    @GetMapping("/history/{userId}")
    public List<Reading> historyReadingsByUserId(@PathVariable("userId") long userId) {
        return monitoringService.historyReadingsByUserId(userId);
    }
}
