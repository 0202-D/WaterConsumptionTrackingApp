package io.ylab.petrov.service.auth;

import io.ylab.petrov.dao.audit.ActionRepository;
import io.ylab.petrov.dao.audit.JdbcActionRepository;
import io.ylab.petrov.dao.user.JdbcUserRepository;
import io.ylab.petrov.dao.user.UserRepository;
import io.ylab.petrov.dto.user.UserRsDto;
import io.ylab.petrov.dto.user.UserRqDto;
import io.ylab.petrov.exception.NotFoundException;
import io.ylab.petrov.mapper.user.UserMapper;
import io.ylab.petrov.mapper.user.UserMapperImpl;
import io.ylab.petrov.model.audit.Action;
import io.ylab.petrov.model.audit.Activity;
import io.ylab.petrov.model.user.Role;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.security.UserPrincipal;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final ActionRepository actionRepository = new JdbcActionRepository();
    private final UserRepository userRepository = new JdbcUserRepository();
    private final UserMapper userMapper = new UserMapperImpl();

    @Override
    public UserRsDto userRegistration(User user) {
        if (!checkExistUserByUserName(user.getUserName())) {
            return null;
        }
        user.setRole(Role.USER);
        userRepository.addUser(user);
        User dbUser = userRepository.getUserByUserName(user.getUserName())
                .orElseThrow(() -> new NotFoundException("Пользователя с таким id не существует"));
        Action action = Action.builder()
                .user(dbUser)
                .activity(Activity.REGISTERED)
                .dateTime(LocalDateTime.now())
                .build();
        actionRepository.addAction(action);
        return userMapper.toDtoRs(dbUser);
    }

    @Override
    public UserRsDto authenticateUser(UserRqDto user) {
        Optional<User> searchUser = userRepository.getUserByUserName(user.getUserName());
        if (searchUser.isEmpty()) {
            return null;
        }
        User dbUser = searchUser.get();
        if (!dbUser.getPassword().equals(user.getPassword())) {
            return null;
        }
        Action action = Action.builder()
                .user(dbUser)
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
