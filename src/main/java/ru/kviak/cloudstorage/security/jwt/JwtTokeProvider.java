package ru.kviak.cloudstorage.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.kviak.cloudstorage.model.Role;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtTokeProvider {
    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private Long validityMilliseconds;

    public String createToken(String username, List<Role> role){

    }

    public Authentication getAuthentication(String token){

    }

    public String getUserName(String token){

    }

    public boolean validateToken(String token){

    }

    private List<String> getRoleNames(List<Role> userRoles){
        List<String> result = new ArrayList<>();

        userRoles.forEach(role -> {
            result.add(role.getName());
        });
        return result;
    }
}
