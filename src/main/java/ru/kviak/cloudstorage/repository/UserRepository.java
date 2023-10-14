package ru.kviak.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kviak.cloudstorage.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByActivationCode(String code);

    Optional<User> findByEmail(String email);

}
