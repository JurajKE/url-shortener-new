package com.example.sortener.security;

import com.example.sortener.repository.AccountRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static java.util.Objects.nonNull;

@Component
@RequiredArgsConstructor
public class AuthentificationProvider implements AuthenticationProvider {
    @NonNull
    private final AccountRepository accountRepository;

    UserDetails isValidUser(String username, String password) {
        var account = accountRepository.findByAccountId(username);
        if (nonNull(account) && account.getPassword().equals(password)) {
            return User
                    .withUsername(username)
                    .password(password)
                    .authorities(new ArrayList<>())
                    .build();
        }
        return null;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        var username = authentication.getName();
        var password = authentication.getCredentials().toString();
        var userDetails = isValidUser(username, password);

        if (userDetails != null) {
            return new UsernamePasswordAuthenticationToken(
                    username,
                    password,
                    new ArrayList<>());
        } else {
            throw new BadCredentialsException("Incorrect user credentials !!");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
