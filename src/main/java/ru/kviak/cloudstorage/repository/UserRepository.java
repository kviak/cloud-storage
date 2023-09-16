package ru.kviak.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kviak.cloudstorage.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
