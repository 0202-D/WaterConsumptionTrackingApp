package io.ylab.petrov.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.Gson;
import io.ylab.petrov.dao.audit.ActionRepository;
import io.ylab.petrov.dao.audit.JdbcActionRepository;
import io.ylab.petrov.dao.user.JdbcUserRepository;
import io.ylab.petrov.dao.user.UserRepository;
import io.ylab.petrov.dto.AddReadingRqDto;
import io.ylab.petrov.dto.ReadingInMonthRq;
import io.ylab.petrov.dto.ReadingRqDto;
import io.ylab.petrov.dto.ReadingRs;
import io.ylab.petrov.exception.ExceptionJson;
import io.ylab.petrov.exception.NotFoundException;
import io.ylab.petrov.in.controller.MonitoringController;
import io.ylab.petrov.model.readout.Reading;
import io.ylab.petrov.model.user.Role;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.security.JwtProvider;
import io.ylab.petrov.service.monitoring.MonitoringService;
import io.ylab.petrov.service.monitoring.MonitoringServiceImpl;
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
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class MonitoringServlet extends HttpServlet {
    public static final String APPLICATION_JSON = "application/json";
    private static final String NOT_FOUND = "Такого пользователя не существует";
    private static final String ACCESS_DENIED = "Доступ запрещен!";
    private final JwtProvider jwtProvider = new JwtProvider();
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final ActionRepository actionRepository;
    private final MonitoringController monitoringController;
    private final MonitoringService monitoringService;
    private Validator validator;

    public MonitoringServlet() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.registerModule(new JavaTimeModule());
        this.actionRepository = new JdbcActionRepository();
        this.userRepository = new JdbcUserRepository();
        this.monitoringService = new MonitoringServiceImpl();
        this.monitoringController = new MonitoringController();
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        if (!jwtProvider.validateJwtToken(req)) {
            returnForbiddenResponse(resp);
        } else {
            String requestURI = req.getRequestURI();
            if (requestURI.contains("user/current")) {
                resp.setContentType(APPLICATION_JSON);
                final Gson gson = new Gson();
                var body = req.getReader();
                final var readingRqDto = gson.fromJson(body, ReadingRqDto.class);
                User userPrincipal = getUserPrincipal(req);
                if (userPrincipal.getId() != readingRqDto.getUserId() && userPrincipal.getRole() != Role.ADMIN) {
                    returnForbiddenResponse(resp);
                    return;
                }
                ReadingRs readingRs = monitoringController.getCurrentReading(readingRqDto);
                if (readingRs == null) {
                    ExceptionJson exceptionJson = ExceptionJson.builder()
                            .message(NOT_FOUND)
                            .httpResponse(HttpServletResponse.SC_NOT_FOUND)
                            .build();
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.setContentType(APPLICATION_JSON);
                    resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(exceptionJson));
                } else {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setContentType(APPLICATION_JSON);
                    resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(readingRs));
                }
            } else if (requestURI.contains("/user/history/")) {
                User userPrincipal = getUserPrincipal(req);
                long userId = getUserId(req);
                if (userPrincipal.getId() != userId && userPrincipal.getRole() != Role.ADMIN) {
                    returnForbiddenResponse(resp);
                    return;
                }
                List<Reading> history = monitoringController.historyReadingsByUserId(userId);
                if (history == null) {
                    ExceptionJson exceptionJson = ExceptionJson.builder()
                            .message(NOT_FOUND)
                            .httpResponse(HttpServletResponse.SC_NOT_FOUND)
                            .build();
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.setContentType(APPLICATION_JSON);
                    resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(exceptionJson));
                } else {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setContentType(APPLICATION_JSON);
                    resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(history));
                }
            } else if (requestURI.contains("/user/month")) {
                final Gson gson = new Gson();
                var body = req.getReader();
                final var readingInMouth = gson.fromJson(body, ReadingInMonthRq.class);
                User userPrincipal = getUserPrincipal(req);
                if (userPrincipal.getId() != readingInMouth.userId() && userPrincipal.getRole() != Role.ADMIN) {
                    returnForbiddenResponse(resp);
                    return;
                }
                Optional<Reading> reading = monitoringController.getReadingForMonth(readingInMouth);
                if (reading.isEmpty()) {
                    ExceptionJson exceptionJson = ExceptionJson.builder()
                            .message(NOT_FOUND)
                            .httpResponse(HttpServletResponse.SC_NOT_FOUND)
                            .build();
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    resp.setContentType(APPLICATION_JSON);
                    resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(exceptionJson));
                } else {
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.setContentType(APPLICATION_JSON);
                    resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(reading));
                }
            } else if (requestURI.contains("/user/add")) {
                resp.setContentType(APPLICATION_JSON);
                final var gson = new Gson();
                var body = req.getReader();
                final var addReadingRqDto = gson.fromJson(body, AddReadingRqDto.class);
                Set<ConstraintViolation<AddReadingRqDto>> violations = validator.validate(addReadingRqDto);
                if (!violations.isEmpty()) {
                    for (ConstraintViolation<AddReadingRqDto> violation : violations) {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        ExceptionJson exceptionJson = ExceptionJson.builder()
                                .message(violation.getMessage())
                                .httpResponse(HttpServletResponse.SC_BAD_REQUEST)
                                .build();
                        resp.setContentType(APPLICATION_JSON);
                        resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(exceptionJson));
                    }
                } else {
                    boolean isOk = monitoringController.addReading(addReadingRqDto);
                    if (!isOk) {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        resp.setContentType(APPLICATION_JSON);
                    } else {
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    }
                }
            }
        }
    }

    private static long getUserId(HttpServletRequest req) {
        String pathInfo = req.getPathInfo();
        int lastIndexOfSlash = pathInfo.lastIndexOf('/');
        String pathVarible = pathInfo.substring(lastIndexOfSlash + 1);
        return Long.parseLong(pathVarible);
    }

    private void returnForbiddenResponse(HttpServletResponse resp) throws IOException {
        ExceptionJson exceptionJson = ExceptionJson.builder()
                .message(ACCESS_DENIED)
                .httpResponse(HttpServletResponse.SC_FORBIDDEN)
                .build();
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setContentType(APPLICATION_JSON);
        resp.getOutputStream().write(this.objectMapper.writeValueAsBytes(exceptionJson));
    }

    private User getUserPrincipal(HttpServletRequest req) {
        String authorizationHeader = req.getHeader("Authorization");
        String token = authorizationHeader.substring(7);
        Long principalId = jwtProvider.getPrincipalId(token);
        return userRepository.getUserById(principalId)
                .orElseThrow(() -> new NotFoundException("Пользователя с таким id не существует"));
    }
}
