package com.example.sortener.security;

import com.example.sortener.entity.Account;
import com.example.sortener.repository.AccountRepository;
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
public class AuthentificaionProvider implements AuthenticationProvider {

    private final AccountRepository accountRepository;

    public AuthentificaionProvider(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    UserDetails isValidUser(String username, String password) {
        Account account = accountRepository.findByAccountId(username);
        if (nonNull(account)) {
            if (account.getPassword().equals(password)) {
                UserDetails user = User
                        .withUsername(username)
                        .password(password)
                        .authorities(new ArrayList<>())
                        .build();
                return user;
            }
        }
        return null;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails = isValidUser(username, password);

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
