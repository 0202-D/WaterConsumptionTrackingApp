package io.ylab.petrov.dao.monitoring;

import io.ylab.petrov.model.readout.Meter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryMeterRepository implements MeterRepository {
    private final List<Meter> meters = new ArrayList<>();

    @Override
    public Meter getMeterById(long id) {
        Optional<Meter> meter = meters.stream().filter(el -> el.getId() == id).findFirst();
        if (meter.isEmpty()) {
            System.out.println("Такого счетчика не существует ");
            return null;
        }
        return meter.get();
    }

    public InMemoryMeterRepository() {
        Meter hotMeter = Meter.builder()
                .id(1L)
                .name("HOT_WATER")
                .build();
        Meter coldMeter = Meter.builder()
                .id(2L)
                .name("COLD_WATER")
                .build();
        Meter heatingMeter = Meter.builder()
                .id(3L)
                .name("HEATING")
                .build();
        meters.add(hotMeter);
        meters.add(coldMeter);
        meters.add(heatingMeter);
    }

    public void save(Meter meter) {
    }
}
