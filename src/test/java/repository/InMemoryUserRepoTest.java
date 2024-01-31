package repository;

import io.ylab.petrov.dao.user.InMemoryUserRepositoryImpl;
import io.ylab.petrov.model.user.Role;
import io.ylab.petrov.model.user.User;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.Assert.*;

public class InMemoryUserRepoTest {
    @Test
    @DisplayName("Тест добавления юзера")
    public void testAddUser() {
        InMemoryUserRepositoryImpl userRepository = new InMemoryUserRepositoryImpl();
        User user = User.builder()
                .id(3L)
                .userName("testUser")
                .password("testUser")
                .role(Role.USER)
                .build();
        userRepository.addUser(user);

        User addedUser = userRepository.getUserById(3L);
        assertNotNull(addedUser);
        assertEquals("testUser", addedUser.getUserName());
    }

    @Test
    @DisplayName("Тест получения юзера по id")
    public void testGetUserById() {
        InMemoryUserRepositoryImpl userRepository = new InMemoryUserRepositoryImpl();

        User user = userRepository.getUserById(1L);

        assertNotNull(user);
        assertEquals("user", user.getUserName());
    }

    @Test
    @DisplayName("Тест получения юзера по имени")
    public void testПetUserByUserName() {
        InMemoryUserRepositoryImpl userRepository = new InMemoryUserRepositoryImpl();

        User user = userRepository.getUserByUserName("admin");

        assertNotNull(user);
        assertEquals("admin", user.getUserName());
    }

    @Test
    @DisplayName("Тест получения юзера по id которого нет в памяти")
    public void testNonExistentId() {
        InMemoryUserRepositoryImpl userRepository = new InMemoryUserRepositoryImpl();

        User user = userRepository.getUserById(100L);

        assertNull(user);
    }
}

