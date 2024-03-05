package service;

import io.ylab.petrov.WaterConsumptionTrackingAppApplication;
import io.ylab.petrov.dao.monitoring.MeterRepository;
import io.ylab.petrov.dao.monitoring.ReadingRepository;
import io.ylab.petrov.dao.user.UserRepository;
import io.ylab.petrov.dto.monitoring.AddReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingResponseDto;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.service.monitoring.MonitoringServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import utils.Utils;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = WaterConsumptionTrackingAppApplication.class)
class MonitoringServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ReadingRepository readingRepository;

    @Mock
    private MeterRepository meterRepository;

    @InjectMocks
    private MonitoringServiceImpl monitoringService;

    @Test
    @Transactional
    @DisplayName("Тест метода внесения показаний")
    void testAddReading() {
        AddReadingRequestDto dto = Utils.getAddReadingRequestDto();

        when(userRepository.getUserById(1L)).thenReturn(Optional.of(Utils.getUser()));
        when(meterRepository.getMeterById(1L)).thenReturn(Utils.getMeter());
        when(readingRepository.getCurrentReading(any())).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> monitoringService.addReading(dto));
    }

    @Test
    @Transactional
    @DisplayName("Тестметода получения текущих показаний")
    void testGetCurrentReading() {
        ReadingRequestDto dto = Utils.getReadingRequestDto();
        User user = Utils.getUser();
        when(userRepository.getUserById(1)).thenReturn(Optional.of(user));
        when(readingRepository.getCurrentReading(dto)).thenReturn(Optional.of(Utils.getReadingResponseDto()));

        Optional<ReadingResponseDto> result = monitoringService.getCurrentReading(dto.getUserId(),dto.getMeterId());

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(new BigDecimal(100), result.get().getReading());
    }
}