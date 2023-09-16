package ru.kviak.cloudstorage.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.kviak.cloudstorage.model.User;
import ru.kviak.cloudstorage.security.jwt.JwtUser;
import ru.kviak.cloudstorage.security.jwt.JwtUserFactory;
import ru.kviak.cloudstorage.service.UserService;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUserName(username);

        if (user == null){
            throw new UsernameNotFoundException("ERROR");
        }
        JwtUser jwtUser = JwtUserFactory.create(user);
        System.out.println("User " + user.getUsername() + " loaded.");
        return jwtUser;
    }
}
