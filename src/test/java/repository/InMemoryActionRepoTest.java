package repository;

import io.ylab.petrov.dao.audit.InMemoryActionRepositoryImpl;
import io.ylab.petrov.model.audit.Action;
import io.ylab.petrov.model.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;


class InMemoryActionRepoTest {
    @Test
    @DisplayName("Тест добавления активности")
    void testAddActionIncreaseSize() {
        InMemoryActionRepositoryImpl repository = new InMemoryActionRepositoryImpl();
        int initialSize = repository.getAllByUserName("testUser").size();
        Action action = Utils.getAction();
        repository.addAction(action);
        int newSize = repository.getAllByUserName("testUser").size();
        Assertions.assertEquals(initialSize + 1, newSize);
    }

    @Test
    @DisplayName("Тест получения всех активностей по имени юзера")
    void testGetAllByUserNameValidUsername() {
        InMemoryActionRepositoryImpl repository = new InMemoryActionRepositoryImpl();
        User user = Utils.getUser();
        Action action1 = Utils.getAction();
        Action action2 = Utils.getAction();
        repository.addAction(action1);
        repository.addAction(action2);
        List<Action> actions = repository.getAllByUserName("testUser");
        Assertions.assertEquals(2, actions.size());
        Assertions.assertTrue(actions.contains(action1));
        Assertions.assertTrue(actions.contains(action2));
    }

    @Test
    @DisplayName("Тест получения активностей по имени при множественных данных ")
    void testGetAllByUserNameMultipleActions() {
        InMemoryActionRepositoryImpl repository = new InMemoryActionRepositoryImpl();
        User user1 = Utils.getUser();
        User user2 = Utils.getSecondUser();
        Action action1 = Utils.getAction();
        Action action2 = Utils.getAction();
        Action action3 = Utils.getActionExited();
        repository.addAction(action1);
        repository.addAction(action2);
        repository.addAction(action3);
        List<Action> actionsUser1 = repository.getAllByUserName("testUser");
        List<Action> actionsUser2 = repository.getAllByUserName("testUser2");
        Assertions.assertEquals(2, actionsUser1.size());
        Assertions.assertEquals(1, actionsUser2.size());
        Assertions.assertTrue(actionsUser1.contains(action1));
        Assertions.assertTrue(actionsUser2.contains(action3));
        Assertions.assertTrue(actionsUser1.contains(action2));
    }
}
