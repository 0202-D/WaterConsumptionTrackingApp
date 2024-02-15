package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.ylab.petrov.dto.user.UserResponseDto;
import io.ylab.petrov.dto.user.UserRequestDto;
import io.ylab.petrov.in.servlet.AuthServlet;
import io.ylab.petrov.model.user.Role;
import io.ylab.petrov.security.JwtProvider;
import io.ylab.petrov.service.auth.AuthService;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Validator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AuthServletTest {
    @Test
    @DisplayName("Тест удачной аутентификации пользователя")
    void testSuccessfulUserAuthentication() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStreamMock = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStreamMock);
        Validator mockedValidator = mock(Validator.class);
        when(mockedValidator.validate(any(UserRequestDto.class)))
                .thenReturn(new HashSet<>());
        String requestBody = "{\"userName\": \"user\", \"password\": \"user\"}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(requestBody)));
        UserResponseDto expectedUserResponseDto = new UserResponseDto(1L, "testUser", Role.USER);
        String expectedToken = "testToken";
        AuthService authServiceMock = mock(AuthService.class);
        when(authServiceMock.authenticateUser(any(UserRequestDto.class))).thenReturn(expectedUserResponseDto);
        JwtProvider jwtProviderMock = mock(JwtProvider.class);
        when(jwtProviderMock.generateAccessJwtToken(expectedUserResponseDto)).thenReturn(expectedToken);
        AuthServlet authServlet = new AuthServlet();
        authServlet.setValidator(mockedValidator);
        authServlet.doPost(request, response);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response, times(2)).setContentType("application/json");
    }
    @Test
    @DisplayName("Тест неудачной аутентификации пользователя")
    void testNotSuccessfulUserAuthentication() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStreamMock = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStreamMock);
        Validator mockedValidator = mock(Validator.class);
        when(mockedValidator.validate(any(UserRequestDto.class)))
                .thenReturn(new HashSet<>());
        String requestBody = "{\"userName\": \"user2\", \"password\": \"user\"}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(requestBody)));
        UserResponseDto expectedUserResponseDto = new UserResponseDto(1L, "testUser", Role.USER);
        String expectedToken = "testToken";
        AuthService authServiceMock = mock(AuthService.class);
        when(authServiceMock.authenticateUser(any(UserRequestDto.class))).thenReturn(expectedUserResponseDto);
        JwtProvider jwtProviderMock = mock(JwtProvider.class);
        when(jwtProviderMock.generateAccessJwtToken(expectedUserResponseDto)).thenReturn(expectedToken);
        AuthServlet authServlet = new AuthServlet();
        authServlet.setValidator(mockedValidator);
        authServlet.doPost(request, response);
        verify(response).setStatus(HttpServletResponse.SC_NOT_FOUND);
    }
}
