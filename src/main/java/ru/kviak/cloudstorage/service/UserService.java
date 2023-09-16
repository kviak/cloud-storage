package ru.kviak.cloudstorage.service;

import ru.kviak.cloudstorage.model.User;

import java.util.List;

public interface UserService {

    User register(User user);

    List<User> getAll();

    User findByUserName(String username);

    User findById(Long id);
}
