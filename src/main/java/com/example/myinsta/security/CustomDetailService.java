package com.example.myinsta.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomDetailService implements UserDetailsService {
    private final JwtMapper jwtMapper;

    @Override
    public UserDetails loadUserByUsername(String email) {
        AccountsDao.AccountDetailsDao userDetailsDao = jwtMapper.findByEmail(email);
        return User.builder()
                .username(String.valueOf(userDetailsDao.getIdAccount()))
                .password(userDetailsDao.getPassword())
                .roles(userDetailsDao.getRole().toString())
                .build();
    }

}