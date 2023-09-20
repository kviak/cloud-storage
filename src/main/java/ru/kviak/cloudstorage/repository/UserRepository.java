package ru.kviak.cloudstorage.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kviak.cloudstorage.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByActivationCode(String code);

    Optional<List<User>> getUserByActivatedTrue();

}
