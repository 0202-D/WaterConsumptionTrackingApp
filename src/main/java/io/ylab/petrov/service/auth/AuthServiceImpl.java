package io.ylab.petrov.service.auth;

import io.ylab.petrov.dao.user.InMemoryUserRepositoryImpl;
import io.ylab.petrov.dao.user.UserRepository;
import io.ylab.petrov.dto.AuthReqDto;
import io.ylab.petrov.model.audit.Action;
import io.ylab.petrov.model.audit.Activity;
import io.ylab.petrov.model.user.User;
import io.ylab.petrov.service.audit.AuditService;
import io.ylab.petrov.service.audit.AuditServiceImpl;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

public class AuthServiceImpl implements AuthService {
    private static AtomicInteger userId = new AtomicInteger(2);
    private final InMemoryUserRepositoryImpl inMemoryUserRepositoryImpl = new InMemoryUserRepositoryImpl();
    private final AuditService auditService = new AuditServiceImpl();
    private final UserRepository userRepository = new InMemoryUserRepositoryImpl();

    @Override
    public boolean userRegistration(User user) {
        User searchUser = userRepository.getUserByUserName(user.getUserName());
        if(searchUser!=null){
            System.out.println("Пользователь с таким именем уже существует");
            return false;
        }
        user.setId(userId.incrementAndGet());
        inMemoryUserRepositoryImpl.addUser(user);
        auditService.addAction(new Action(user, Activity.REGISTERED, LocalDateTime.now()));
        return true;
    }

    @Override
    public boolean authenticateUser(AuthReqDto user) {
        Optional<User> searchUser = inMemoryUserRepositoryImpl.getUsers().stream()
                .filter(el -> el.getUserName().equals(user.userName())).findFirst();
        if (searchUser.isEmpty()) {
            System.out.println("Пользователя с таким именем не существует");
            return false;
        }
        auditService.addAction(new Action(searchUser.get(), Activity.ENTERED, LocalDateTime.now()));
        return searchUser.get().getPassword().equals(user.password());
    }
}
