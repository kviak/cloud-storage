package ru.kviak.cloudstorage.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kviak.cloudstorage.model.Role;
import ru.kviak.cloudstorage.model.User;
import ru.kviak.cloudstorage.repository.RoleRepository;
import ru.kviak.cloudstorage.repository.UserRepository;
import ru.kviak.cloudstorage.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);

        User registeredUser = userRepository.save(user);
        System.out.println("Пользователь сохранен!");
        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        System.out.println("All user gets, count-" + result.size());
        return result;
    }

    @Override
    public User findByUserName(String username) {
        User result = userRepository.findByUsername(username);
        System.out.println("Найден пользователь" + result);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);
        System.out.println("Найден пользователь" + result);
        return result;
    }
}
