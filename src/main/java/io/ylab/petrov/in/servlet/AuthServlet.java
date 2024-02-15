package io.ylab.petrov.in.servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import io.ylab.petrov.dao.audit.ActionRepository;
import io.ylab.petrov.dao.audit.JdbcActionRepository;
import io.ylab.petrov.dao.user.JdbcUserRepository;
import io.ylab.petrov.dao.user.UserRepository;
import io.ylab.petrov.dto.user.UserResponseDto;
import io.ylab.petrov.dto.user.UserRequestDto;
import io.ylab.petrov.exception.ExceptionJson;
import io.ylab.petrov.in.controller.AuthController;
import io.ylab.petrov.security.JwtProvider;
import io.ylab.petrov.service.auth.AuthService;
import io.ylab.petrov.service.auth.AuthServiceImpl;
import io.ylab.petrov.utils.DbStarter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.io.IOException;
import java.util.Set;

public class AuthServlet extends HttpServlet {
    public static final String APPLICATION_JSON = "application/json";
    private static final String NOT_FOUND = "Такого пользователя не существует";
    private final ObjectMapper objectMapper;
    private final AuthController authController;
    private final ActionRepository actionRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private Validator validator;
    private final JwtProvider jwtProvider;


    public AuthServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.actionRepository = new JdbcActionRepository();
        this.userRepository = new JdbcUserRepository();
        this.authService = new AuthServiceImpl();
        this.authController = new AuthController(authService);
        this.jwtProvider = new JwtProvider();
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
            DbStarter starter = new DbStarter();
            starter.start();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(APPLICATION_JSON);
        final var gson = new Gson();
        var body = req.getReader();
        UserRequestDto userDto = gson.fromJson(body, UserRequestDto.class);
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userDto);
        if (!violations.isEmpty()) {
            for (ConstraintViolation<UserRequestDto> violation : violations) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                ExceptionJson exceptionJson = ExceptionJson.builder()
                        .message(violation.getMessage())
                        .httpResponse(HttpServletResponse.SC_BAD_REQUEST)
                        .build();
                resp.setContentType(APPLICATION_JSON);
                resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(exceptionJson));
            }
        } else {
            UserResponseDto userResponseDto = authController.authenticateUser(userDto);
            if (userResponseDto != null) {
                String token = jwtProvider.generateAccessJwtToken(userResponseDto);
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.setContentType(APPLICATION_JSON);
                resp.addHeader("Authorization", "Bearer " + token);
                resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(userResponseDto));
            } else {
                ExceptionJson exceptionJson = ExceptionJson.builder()
                        .message(NOT_FOUND)
                        .httpResponse(HttpServletResponse.SC_NOT_FOUND)
                        .build();
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(exceptionJson));
            }
        }
    }

    public void setValidator(Validator validator) {
        this.validator = validator;
    }
}