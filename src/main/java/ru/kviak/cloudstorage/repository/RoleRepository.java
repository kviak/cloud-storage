package ru.kviak.cloudstorage.repository;

import org.springframework.data.repository.CrudRepository;
import ru.kviak.cloudstorage.model.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByName(String name);

}
