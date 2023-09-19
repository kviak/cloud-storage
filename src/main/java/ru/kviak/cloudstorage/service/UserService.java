package ru.kviak.cloudstorage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kviak.cloudstorage.dto.RegistrationUserDto;
import ru.kviak.cloudstorage.exception.UserAlreadyActivatedException;
import ru.kviak.cloudstorage.exception.UserNotFoundException;
import ru.kviak.cloudstorage.model.User;
import ru.kviak.cloudstorage.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService mailSender;
    @Value("${application.url}")
    private String url;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("Пользователь '%s' не найден", username)
        ));
        if (!user.isActivated()) throw new UsernameNotFoundException("This account not activated please check you email and confirm email!");
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }
    @Transactional
    public User createNewUser(RegistrationUserDto registrationUserDto) {
        User user = new User();
        user.setUsername(registrationUserDto.getUsername());
        user.setEmail(registrationUserDto.getEmail());
        user.setPassword(passwordEncoder.encode(registrationUserDto.getPassword())); // TODO: MapStruct напрашивается.
        user.setRoles(List.of(roleService.getUserRole()));
        user.setActivationCode(UUID.randomUUID().toString());

        if (!user.getEmail().isBlank()){
            String message = url + "activate/"+user.getActivationCode();
            mailSender.send(user.getEmail(), "Activation code: ", message);
        }
        return userRepository.save(user);
    }
    @Transactional
    public void activateUser(String code) {
        User user = userRepository.findByActivationCode(code)
                .orElseThrow(UserNotFoundException::new);
        if (user.isActivated()) throw new UserAlreadyActivatedException();
            else { user.setActivated(true);
        }
    }
}
