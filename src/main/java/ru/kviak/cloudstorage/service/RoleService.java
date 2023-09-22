package ru.kviak.cloudstorage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.kviak.cloudstorage.model.Role;
import ru.kviak.cloudstorage.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    static final String ROLE_USER="ROLE_USER";
    private final RoleRepository roleRepository;

    @Cacheable("roles")
    public Role getUserRole() {
        return roleRepository.findByName(ROLE_USER).get();
    }
}
