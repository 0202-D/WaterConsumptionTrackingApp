package io.ylab.petrov.dao.monitoring;

import io.ylab.petrov.model.readout.Meter;

public interface MeterRepository {
    Meter getMeterById(long id);
}
