package ru.clevertec.newspaper.config.sequrity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.clevertec.newspaper.config.sequrity.user.UserClient;
import ru.clevertec.newspaper.config.sequrity.user.dto.AppUser;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DetailsService implements UserDetailsService {

    private final UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser userByUsername = userClient.getUserByUsername(username);

        Set<GrantedAuthority> authorities = userByUsername.roles().stream()
            .map((roles) -> new SimpleGrantedAuthority(roles.roleName()))
            .collect(Collectors.toSet());

        return new User(username, userByUsername.password(), authorities);

    }
}
