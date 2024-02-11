package io.ylab.petrov.mapper.user;


import io.ylab.petrov.dto.user.UserRsDto;
import io.ylab.petrov.dto.user.UserRqDto;
import io.ylab.petrov.model.user.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {
    UserRsDto toDtoRs(User user);
    User toEntity(UserRqDto userDto);
}
