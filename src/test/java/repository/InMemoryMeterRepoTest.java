package repository;

import io.ylab.petrov.dao.monitoring.InMemoryMeterRepository;
import io.ylab.petrov.model.readout.Meter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class InMemoryMeterRepoTest {
    @Test
    @DisplayName("Тест получения счетчика по его id")
    void testRetrieveMeterById() {
        InMemoryMeterRepository repository = new InMemoryMeterRepository();
        Meter meter = repository.getMeterById(1L);
        Assertions.assertNotNull(meter);
        Assertions.assertEquals(1L, meter.getId());
        Assertions.assertEquals("HOT_WATER", meter.getName());
    }

    @Test
    @DisplayName("Тест получения не существующего счетчика")
    void testRetrieveNonexistentMeter() {
        InMemoryMeterRepository repository = new InMemoryMeterRepository();
        Meter meter = repository.getMeterById(4L);
        Assertions.assertNull(meter);
    }
}
