package io.ylab.petrov.mapper.user;


import io.ylab.petrov.dto.user.UserResponseDto;
import io.ylab.petrov.dto.user.UserRequestDto;
import io.ylab.petrov.model.user.User;

public class UserMapperImpl implements UserMapper {
    @Override
    public UserResponseDto toDtoRs(User user) {
        return UserResponseDto.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .role(user.getRole())
                .build();
    }


    @Override
    public User toEntity(UserRequestDto userDto) {
        return User.builder()
                .userName(userDto.getUserName())
                .password(userDto.getPassword())
                .build();

    }
}
