package repository;

import io.ylab.petrov.WaterConsumptionTrackingAppApplication;
import io.ylab.petrov.dao.monitoring.JdbcReadingRepository;
import io.ylab.petrov.dao.monitoring.MeterRepository;
import io.ylab.petrov.dao.user.UserRepository;
import io.ylab.petrov.dto.monitoring.ReadingInMonthRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingResponseDto;
import io.ylab.petrov.model.readout.Meter;
import io.ylab.petrov.model.readout.Reading;
import io.ylab.petrov.model.user.User;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import utils.Utils;

import java.math.BigDecimal;
import java.time.Month;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WaterConsumptionTrackingAppApplication.class)
class JdbcReadingRepositoryTest {
    @MockBean
    private UserRepository userRepository;

    @MockBean
    private MeterRepository meterRepository;

    @InjectMocks
    @Autowired
    private JdbcReadingRepository jdbcReadingRepository;

    @Test
    @Transactional
    @DisplayName("Тест получения текущих показаний")
    void testGetCurrentReading() {
        ReadingRequestDto dto = Utils.getReadingRequestDto();
        User user = Utils.getUser();
        Mockito.when(userRepository.getUserById(1L)).thenReturn(Optional.of(user));
        Meter meter = Utils.getMeter();
        Mockito.when(meterRepository.getMeterById(2L)).thenReturn(meter);
        Optional<ReadingResponseDto> result = jdbcReadingRepository.getCurrentReading(dto);
        assertTrue(result.isPresent());
        Assert.assertEquals(new BigDecimal(555555), result.get().getReading());
    }

    @Test
    @Transactional
    @DisplayName("Тест получения показаний за кокретный месяц")
    void testGetReadingForMonth() {
        ReadingInMonthRequestDto rq = Utils.getReadingInMonthRequest();
        User user = Utils.getUser();
        Mockito.when(userRepository.getUserById(anyLong())).thenReturn(Optional.ofNullable(user));
        Meter meter = Utils.getMeter();
        Mockito.when(meterRepository.getMeterById(1L)).thenReturn(meter);
        Optional<Reading> result = jdbcReadingRepository.getReadingForMonth(rq);
        assertTrue(result.isPresent());
        Assert.assertEquals(Month.FEBRUARY, result.get().getDate().getMonth());
    }
    @Test
    @Transactional
    void testHistoryReadingsByUserId() {
        User user = Utils.getUser();
        Mockito.when(userRepository.getUserById(anyLong())).thenReturn(Optional.ofNullable(user));
        List<Reading> historyReadings = jdbcReadingRepository.historyReadingsByUserId(1L);
        assertNotNull(historyReadings);
        assertEquals(1, historyReadings.size());
    }
}
