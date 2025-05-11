package com.example.db.service;

import com.example.db.entity.Authority;
import com.example.db.entity.User;
import com.example.db.repository.AuthorityRepository;
import com.example.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean existsUsername(String username) {
        return userRepository.existsById(username);
    }

    @Transactional
    public void register(String username, String rawPassword) {
        // Encrypt password
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // (1) First, save the user
        User newUser = new User(username, encodedPassword, true, null, null);
        userRepository.save(newUser);

        // (2) Then, save authority for that user
        Authority authority = new Authority(null, newUser, "ROLE_USER");
        authorityRepository.save(authority);

        newUser.setAuthorities(Set.of(authority));
    }

    public long numberOfUsers() {
        return userRepository.count();
    }
}
