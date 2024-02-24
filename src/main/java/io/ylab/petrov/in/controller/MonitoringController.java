package io.ylab.petrov.in.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.ylab.petrov.dto.monitoring.AddReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingInMonthRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingResponseDto;
import io.ylab.petrov.model.readout.Reading;
import io.ylab.petrov.service.monitoring.MonitoringService;
import jakarta.validation.Valid;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@Tag(name = "Подача и просмотр показаний счетчиков")
@Data
@RequiredArgsConstructor
@RestController
@RequestMapping("/monitoring")
public class MonitoringController {
    private final MonitoringService monitoringService;
    @Operation(summary = "Внесение показаний")
    @PostMapping()
    public ResponseEntity addReading(@RequestBody @Valid AddReadingRequestDto dto) {
        monitoringService.addReading(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Operation(summary = "Просмотр текущих показаний")
    @PostMapping("/getcurrent")
    public ReadingResponseDto getCurrentReading(@RequestBody @Valid ReadingRequestDto dto) {
        Optional<ReadingResponseDto> reading = monitoringService.getCurrentReading(dto);
        return reading.orElse(null);
    }
    @Operation(summary = "Просмотр показаний за конкретный месяц")
    @PostMapping("/getbymonth")
    public Optional<Reading> getReadingForMonth(@RequestBody @Valid ReadingInMonthRequestDto rq) {
        return monitoringService.getReadingForMonth(rq);
    }
    @Operation(summary = "Просмотр истории показаний")
    @GetMapping("/history/{userId}")
    public List<Reading> historyReadingsByUserId(@PathVariable("userId") long userId) {
        return monitoringService.historyReadingsByUserId(userId);
    }
}
