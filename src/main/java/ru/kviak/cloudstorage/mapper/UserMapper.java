package ru.kviak.cloudstorage.mapper;

import org.mapstruct.Mapper;
import ru.kviak.cloudstorage.dto.RegistrationUserDto;
import ru.kviak.cloudstorage.dto.UserDto;
import ru.kviak.cloudstorage.model.Role;
import ru.kviak.cloudstorage.model.User;

import java.util.List;
import java.util.UUID;


@Mapper(componentModel = "Spring")
public interface UserMapper {

    UserDto mapTo(User user);

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
