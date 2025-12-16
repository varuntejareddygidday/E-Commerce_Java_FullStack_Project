package com.example.ecommerce.service;

import com.example.ecommerce.model.User;
import com.example.ecommerce.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        String normalizedEmail = normalizeEmail(email);

        User u = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + normalizedEmail));

        return new org.springframework.security.core.userdetails.User(
                u.getEmail(),
                u.getPassword(),
                getAuthorities(u.getRole())
        );
    }

    public String register(String name, String email, String rawPassword) {
        String normalizedEmail = normalizeEmail(email);

        if (userRepository.existsByEmail(normalizedEmail)) {
            return "Email already exists";
        }

        User u = new User();
        u.setName(name);
        u.setEmail(normalizedEmail);
        u.setPassword(passwordEncoder.encode(rawPassword));
        u.setRole("USER");

        userRepository.save(u);
        return null; // null = success
    }

    private String normalizeEmail(String email) {
        if (email == null) return null;
        return email.trim().toLowerCase();
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String roleFromDb) {
        String role = (roleFromDb == null) ? "USER" : roleFromDb.trim().toUpperCase();

        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }

        return List.of(new SimpleGrantedAuthority(role));
    }
}
