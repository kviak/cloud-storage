package ru.kviak.cloudstorage.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kviak.cloudstorage.dto.RegistrationUserDto;
import ru.kviak.cloudstorage.dto.UserDto;
import ru.kviak.cloudstorage.exception.UserAlreadyActivatedException;
import ru.kviak.cloudstorage.exception.UserNotFoundException;
import ru.kviak.cloudstorage.model.User;
import ru.kviak.cloudstorage.repository.UserRepository;
import ru.kviak.cloudstorage.mapper.UserMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final EmailSenderService mailSender;
    private final UserMapper userMapper;
    private final JdbcTemplate jdbcTemplate;

    @Value("${application.url}")
    private String url;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
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
        User user = userMapper.mapTo(registrationUserDto,
                passwordEncoder.encode(registrationUserDto.getPassword()),
                List.of(roleService.getUserRole()));
        if (!user.getEmail().isBlank()){
            String message = url + "activate/"+user.getActivationCode();
            mailSender.send(user.getEmail(), "Activation code: ", message);
        }
        return userRepository.save(user);
    }
    @Transactional
    public void activateUser(String code) {
        User user = userRepository.findByActivationCode(code).orElseThrow(UserNotFoundException::new);
        if (user.isActivated()) throw new UserAlreadyActivatedException();
            else {
                user.setActivated(true);
            }
    }

    public List<UserDto> getAllUsers(int offset, int limit){
        List<UserDto> list = new ArrayList<>();
        userRepository.findAll(PageRequest.of(offset, limit))
                .forEach(user -> {
            list.add(userMapper.mapTo(user));
        });
        return list;
    }

    @Transactional
    public boolean setVipRole(long userId){
        jdbcTemplate.update ("INSERT INTO users_roles VALUES("+userId+", 2);");
        return true;
    }

}
