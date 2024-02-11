package io.ylab.petrov.mapper.user;


import io.ylab.petrov.dto.user.UserRsDto;
import io.ylab.petrov.dto.user.UserRqDto;
import io.ylab.petrov.model.user.User;

public class UserMapperImpl implements UserMapper {
    @Override
    public UserRsDto toDtoRs(User user) {
        return UserRsDto.builder()
                .userId(user.getId())
                .userName(user.getUserName())
                .role(user.getRole())
                .build();
    }


    @Override
    public User toEntity(UserRqDto userDto) {
        return User.builder()
                .userName(userDto.getUserName())
                .password(userDto.getPassword())
                .build();

    }
}
