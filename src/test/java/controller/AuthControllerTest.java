package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.petrov.dto.user.UserRequestDto;
import io.ylab.petrov.dto.user.UserResponseDto;
import io.ylab.petrov.in.controller.AuthController;
import io.ylab.petrov.model.user.Role;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.service.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import repository.Utils;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@ExtendWith(MockitoExtension.class)
class AuthControllerTest{
    private MockMvc mockMvc;

    @Mock
    private AuthService authService;

    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authController = new AuthController(authService);
        mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    void testAddUser() throws Exception {
        User user = new User();
        // Здесь у вас нужно создать тестового пользователя с данными

      //  given(authService.userRegistration(any(User.class))).willReturn(new UserResponseDto("User registered"));

        mockMvc.perform(post("/reg")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    void testAuthenticateUser() throws Exception {
        UserRequestDto userRequestDto = new UserRequestDto();
        // Здесь у вас нужно создать объект ожидаемого запроса для аутентификации

       // given(authService.authenticateUser(any(UserRequestDto.class))).willReturn(new UserResponseDto("User authenticated"));

        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(userRequestDto)))
                .andExpect(status().isOk());
    }
}

