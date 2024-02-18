package io.ylab.petrov.security;

import io.ylab.petrov.model.user.Role;
import lombok.Builder;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.security.Principal;
@Builder
@Data
public class UserPrincipal {
    private Long userId;
    private Role role;

    public UserPrincipal(Long userId, Role role) {
        this.userId = userId;
        this.role = role;
    }
}
