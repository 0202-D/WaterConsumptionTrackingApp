package io.ylab.petrov.io.model.user;

import io.ylab.petrov.io.model.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private String userName;
    private String login;
    private Role role;

}
