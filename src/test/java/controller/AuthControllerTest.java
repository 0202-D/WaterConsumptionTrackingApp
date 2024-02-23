package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.petrov.WaterConsumptionTrackingAppApplication;
import io.ylab.petrov.dto.user.UserRequestDto;
import io.ylab.petrov.dto.user.UserResponseDto;
import io.ylab.petrov.exception.IncorrectDataException;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import repository.Utils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = WaterConsumptionTrackingAppApplication.class)
class AuthControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private AuthService authService;

    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Тест регистрации пользователя")
    void testAddUser() throws Exception {
        User user = Utils.getUser();
        UserResponseDto userResponse = Utils.getUserResponseDto();
        String givenDtoJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
        when(authService.userRegistration(any(User.class))).thenReturn(userResponse);

        mockMvc.perform(post("/reg")
                        .contentType(MediaType.APPLICATION_JSON).content(givenDtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(userResponse.getUserName()));
    }

    @Test
    @DisplayName("Тест аутентификации пользователя")
    void testAuthenticateUser() throws Exception {
        UserRequestDto userRequestDto = Utils.getUserRequestDto();
        UserResponseDto userResponse = Utils.getUserResponseDto();
        String givenDtoJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userRequestDto);
        when(authService.authenticateUser(any(UserRequestDto.class))).thenReturn(userResponse);

        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenDtoJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userName").value(userResponse.getUserName()));
    }

    @Test
    @DisplayName("Тест неудачной аутентификации пользователя")
    void testAuthenticateUserNotSuccess() throws Exception {
        UserRequestDto userRequestDto = Utils.getUserRequestDto();
        String givenDtoJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userRequestDto);
        when(authService.authenticateUser(any(UserRequestDto.class))).thenThrow(new IncorrectDataException("Не корректные данные"));

        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(givenDtoJson))
                .andExpect(status().isBadRequest());
    }
}

