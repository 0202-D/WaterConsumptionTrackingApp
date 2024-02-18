package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.petrov.dto.monitoring.AddReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingInMonthRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingRequestDto;
import io.ylab.petrov.dto.monitoring.ReadingResponseDto;
import io.ylab.petrov.in.controller.MonitoringController;
import io.ylab.petrov.service.monitoring.MonitoringService;
import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import repository.Utils;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class MonitoringControllerTest {
    @Mock
    private MonitoringService monitoringService;

    private MockMvc mockMvc;

    @InjectMocks
    private MonitoringController monitoringController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(monitoringController).build();
    }

    @Test
    @DisplayName("тест добавления показаний")
    void testAddReading() throws Exception {
        ReadingResponseDto responseDto = new ReadingResponseDto();
        when(monitoringService.addReading(any(AddReadingRequestDto.class))).thenReturn(true);
        mockMvc.perform(post("/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(responseDto)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("тест получения текущих показаний")
    void testGetCurrentReading() throws Exception {
        ReadingResponseDto readingResponseDto = Utils.getReadingResponseDto();
        when(monitoringService.getCurrentReading(any(ReadingRequestDto.class))).thenReturn(Optional.of(readingResponseDto));
        mockMvc.perform(post("/getcurrent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new ReadingRequestDto())))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("тест получения показаний за месяц")
    void testGetReadingForMonth() throws Exception {
        ReadingInMonthRequestDto inMonthRequestDto = Utils.getReadingInMonthRequest();
        when(monitoringService.getReadingForMonth(any(ReadingInMonthRequestDto.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/getbymonth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(inMonthRequestDto)))
                .andExpect(status().isOk());
    }
}
