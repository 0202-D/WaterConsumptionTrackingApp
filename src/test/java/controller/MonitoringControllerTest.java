package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.petrov.WaterConsumptionTrackingAppApplication;
import io.ylab.petrov.dto.monitoring.AddReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingResponseDto;
import io.ylab.petrov.model.readout.Reading;
import io.ylab.petrov.service.auth.AuthService;
import io.ylab.petrov.service.monitoring.MonitoringService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import utils.Utils;

import java.math.BigDecimal;
import java.time.Month;
 import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = WaterConsumptionTrackingAppApplication.class)
class MonitoringControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private MonitoringService monitoringService;
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testAddReading() throws Exception {
        AddReadingRequestDto request = Utils.getAddReadingRequestDto();
        String givenDtoJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(request);
        when(monitoringService.addReading(any())).thenReturn(true);
        mockMvc.perform(post("/monitoring")
                        .contentType(MediaType.APPLICATION_JSON).content(givenDtoJson))
                .andExpect(status().isOk());
    }

    @Test
    void testGetCurrentReading() throws Exception {
        long userId = 1L;
        long meterId = 1L;
        ReadingResponseDto response = Utils.getReadingResponseDto();
        Mockito.when(monitoringService.getCurrentReading(userId, meterId)).thenReturn(Optional.of(response));
        mockMvc.perform(get("/monitoring/{userId}/{meterId}", userId, meterId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.reading").value(new BigDecimal(100)));
    }

    @Test
    void testGetReadingForMonth() throws Exception {
        long userId = 1L;
        long meterId = 1L;
        Month month = Month.JANUARY;
        Reading reading = Utils.getReading();
        Mockito.when(monitoringService.getReadingForMonth(userId, meterId, month)).thenReturn(Optional.of(reading));
        mockMvc.perform(get("/monitoring/month/{userId}/{meterId}/{month}", userId, meterId, month))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.meterReading").value(new BigDecimal(1000)));
    }
}

