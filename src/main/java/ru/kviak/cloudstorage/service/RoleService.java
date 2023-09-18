package ru.kviak.cloudstorage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kviak.cloudstorage.model.Role;
import ru.kviak.cloudstorage.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    public Role getUserRole() {
        return roleRepository.findByName("USER_ROLE").get();
    }
}
