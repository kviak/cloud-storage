package ru.kviak.cloudstorage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kviak.cloudstorage.model.Role;
import ru.kviak.cloudstorage.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getUserRole() {
        return roleRepository.findByName("USER_ROLE").get();
    }
}
