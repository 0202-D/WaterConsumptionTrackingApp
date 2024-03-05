package io.ylab.petrov.mapper.user;


import io.ylab.petrov.dto.user.UserResponseDto;
import io.ylab.petrov.dto.user.UserRequestDto;
import io.ylab.petrov.model.user.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponseDto toUserResponseDto(User user);
    User toEntity(UserRequestDto userDto);
}
