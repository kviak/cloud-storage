package ru.kviak.cloudstorage.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.kviak.cloudstorage.dto.UserDto;
import ru.kviak.cloudstorage.model.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    default UserDto mapTo(User user){
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }
}
