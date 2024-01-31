package repository;

import io.ylab.petrov.model.audit.Action;
import io.ylab.petrov.model.audit.Activity;
import io.ylab.petrov.model.user.Role;
import io.ylab.petrov.model.user.User;

import java.time.LocalDateTime;

public class Utils {
    public static User getUser() {
        return User.builder()
                .id(1L)
                .userName("testUser")
                .password("testPassword")
                .role(Role.USER)
                .build();
    }
    public static User getSecondUser() {
        return User.builder()
                .id(1L)
                .userName("testUser2")
                .password("testPassword2")
                .role(Role.USER)
                .build();
    }
    public static Action getAction() {
        return Action.builder()
                .user(getUser())
                .activity(Activity.ENTERED)
                .dateTime(LocalDateTime.now())
                .build();
    }
    public static Action getActionExited() {
        return Action.builder()
                .user(getSecondUser())
                .activity(Activity.EXITED)
                .dateTime(LocalDateTime.now())
                .build();
    }
}
