package repository;

import io.ylab.petrov.dao.monitoring.InMemoryReadingRepositoryImpl;
import io.ylab.petrov.dto.ReadingInMonthRq;
import io.ylab.petrov.dto.ReadingRqDto;
import io.ylab.petrov.model.readout.Meter;
import io.ylab.petrov.model.readout.Reading;
import io.ylab.petrov.model.user.Role;
import io.ylab.petrov.model.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;


class InMemoryReadingRepoTest {
    @Test
    @DisplayName("Тест добавления показаний")
    void testAddReading() {
        InMemoryReadingRepositoryImpl repository = new InMemoryReadingRepositoryImpl();
        Reading reading = new Reading();
        repository.addReading(reading);
        Assertions.assertTrue(repository.getReadings().contains(reading));
    }

    @Test
    @DisplayName("Тест получения текущих показаний")
    void testGetCurrentReading() {
        InMemoryReadingRepositoryImpl repository = new InMemoryReadingRepositoryImpl();
        Reading reading1 = new Reading();
        reading1.setUser(new User(1, "user1", "login1", Role.USER));
        reading1.setMeter(new Meter(1, "meter1"));
        reading1.setCurrent(true);
        repository.addReading(reading1);

        Reading reading2 = new Reading();
        reading2.setUser(new User(2, "user2", "login2", Role.USER));
        reading2.setMeter(new Meter(2, "meter2"));
        reading2.setCurrent(false);
        repository.addReading(reading2);

        ReadingRqDto dto = new ReadingRqDto(1, 1);
        Optional<Reading> result = repository.getCurrentReading(dto);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(reading1, result.get());
    }

    @Test
    @DisplayName("Тест получения показаний за определенный месяц")
    void testGetReadingForMonth() {
        InMemoryReadingRepositoryImpl repository = new InMemoryReadingRepositoryImpl();
        Reading reading1 = new Reading();
        reading1.setUser(new User(1, "user1", "login1", Role.USER));
        reading1.setMeter(new Meter(1, "meter1"));
        reading1.setDate(LocalDate.of(2022, 1, 1));
        repository.addReading(reading1);

        Reading reading2 = new Reading();
        reading2.setUser(new User(2, "user2", "login2", Role.USER));
        reading2.setMeter(new Meter(2, "meter2"));
        reading2.setDate(LocalDate.of(2022, 2, 1));
        repository.addReading(reading2);

        ReadingInMonthRq rq = new ReadingInMonthRq(1, 1, Month.JANUARY);
        Optional<Reading> result = repository.getReadingForMonth(rq);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(reading1, result.get());
    }
}
