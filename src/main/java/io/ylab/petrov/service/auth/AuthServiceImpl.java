package io.ylab.petrov.service.auth;

import io.ylab.loggableaspectstarter.aop.annotation.Loggable;
import io.ylab.petrov.dao.audit.ActionRepository;
import io.ylab.petrov.dao.user.UserRepository;
import io.ylab.petrov.dto.user.UserResponseDto;
import io.ylab.petrov.dto.user.UserRequestDto;
import io.ylab.petrov.exception.IncorrectDataException;
import io.ylab.petrov.exception.NotFoundException;
import io.ylab.petrov.mapper.user.UserMapper;
import io.ylab.petrov.model.audit.Action;
import io.ylab.petrov.model.audit.Activity;
import io.ylab.petrov.model.user.Role;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {
    private final ActionRepository actionRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto userRegistration(User user) {
        if (!checkExistUserByUserName(user.getUserName())) {
            throw new IncorrectDataException("Имя уже занято");
        }
        user.setRole(Role.USER);
        userRepository.addUser(user);
        User dbUser = userRepository.getUserByUserName(user.getUserName())
                .orElseThrow(() -> new NotFoundException("Пользователя с таким id не существует"));
        Action action = Action.builder()
                .userId(dbUser.getId())
                .activity(Activity.REGISTERED)
                .dateTime(LocalDateTime.now())
                .build();
        actionRepository.addAction(action);
        return userMapper.toDtoRs(dbUser);
    }

    @Override
    @Loggable
    public UserResponseDto authenticateUser(UserRequestDto user) {
        User dbUser = userRepository.getUserByUserName(user.getUserName())
                .orElseThrow(() -> new NotFoundException("Пользователя с таким именем не зарегестрировано"));
        if (!dbUser.getPassword().equals(user.getPassword())) {
            throw new IncorrectDataException("Не верный пароль!");
        }
        Action action = Action.builder()
                .userId(dbUser.getId())
                .activity(Activity.ENTERED)
                .dateTime(LocalDateTime.now())
                .build();
        actionRepository.addAction(action);
        UserPrincipal userPrincipal = UserPrincipal.builder()
                .userId(dbUser.getId())
                .role(dbUser.getRole())
                .build();
        return userMapper.toDtoRs(dbUser);
    }

    @Override
    public Optional<User> getUserByUserName(String userName) {
        return userRepository.getUserByUserName(userName);
    }

    public boolean checkExistUserByUserName(String userName) {
        Optional<User> dbUser = userRepository.getUserByUserName(userName);
        return dbUser.isEmpty();
    }
}
