package ru.kviak.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.kviak.cloudstorage.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
