package servlet;

import io.ylab.petrov.dto.user.UserResponseDto;
import io.ylab.petrov.dto.user.UserRequestDto;
import io.ylab.petrov.in.controller.AuthController;
import io.ylab.petrov.in.servlet.RegistrationServlet;
import io.ylab.petrov.model.user.Role;
import io.ylab.petrov.model.user.User;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashSet;

import static org.hamcrest.CoreMatchers.any;
import static org.mockito.Mockito.*;

class RegistrationServletTest {
    @Test
    void testValidUserRegistration() throws IOException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        ServletOutputStream outputStreamMock = mock(ServletOutputStream.class);
        when(response.getOutputStream()).thenReturn(outputStreamMock);
        Validator mockedValidator = mock(Validator.class);
        when(mockedValidator.validate(any(UserRequestDto.class)))
                .thenReturn(new HashSet<>());
        String requestBody = "{\"userName\": \"testUser\", \"password\": \"testPassword\"}";
        when(request.getReader()).thenReturn(new BufferedReader(new StringReader(requestBody)));
        UserResponseDto expectedUserResponseDto = new UserResponseDto(1L, "testUser", Role.USER);
        AuthController authControllerMock = mock(AuthController.class);
        when(authControllerMock.addUser(new User(null, "testUser", "testPassword", Role.USER)))
                .thenReturn(expectedUserResponseDto);
        RegistrationServlet registrationServlet = new RegistrationServlet();
        registrationServlet.setValidator(mockedValidator);
        registrationServlet.doPost(request, response);
        verify(response).setStatus(HttpServletResponse.SC_OK);
        verify(response, times(2)).setContentType("application/json");
    }
}