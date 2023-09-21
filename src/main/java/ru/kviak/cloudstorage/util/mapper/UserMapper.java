package ru.kviak.cloudstorage.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.kviak.cloudstorage.dto.RegistrationUserDto;
import ru.kviak.cloudstorage.dto.UserDto;
import ru.kviak.cloudstorage.model.Role;
import ru.kviak.cloudstorage.model.User;

import java.util.List;
import java.util.UUID;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    default UserDto mapTo(User user){
        return new UserDto(user.getId(), user.getUsername(), user.getEmail());
    }

    default User mapTo(RegistrationUserDto userDto, String encodePassword, List<Role> roles){
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(encodePassword);
        user.setRoles(roles);
        user.setActivationCode(UUID.randomUUID().toString());
        return user;
    }
}
