package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.petrov.dto.user.UserRequestDto;
import io.ylab.petrov.dto.user.UserResponseDto;
import io.ylab.petrov.in.controller.AuthController;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import repository.Utils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
class AuthControllerTest{
    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    @DisplayName("Должен успешно аутентифицировать существующего пользователя")
    void testAuthenticateUser() throws Exception {
        UserRequestDto userRqDto = Utils.getUserRequestDto();
        UserResponseDto dto = Utils.getUserResponseDto();
        Mockito.when(authService.authenticateUser(userRqDto)).thenReturn(dto);
        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRqDto)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("user"));
    }

    @Test
    @DisplayName(" Должен успешно зарегестрировать пользователя")
    void testAddUser() throws Exception {
        User user = Utils.getUser();
        UserResponseDto dto = Utils.getUserResponseDto();
        Mockito.when(authService.userRegistration(user)).thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.post("/reg")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName").value("user"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value("1"));
    }
}

