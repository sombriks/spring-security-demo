package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {

    @Bean
    public UserDetailsService userDetailsService(MyLoginRepository repository) {
        return username -> {
            // create a custom call on your repository to return MyLogin by username
            // Optional<MyLogin> getByLogin(String login);
            return repository
                    .getByLogin(username)
                    .map(UserDetailsDTO::new)
                    .orElseThrow(() -> new UsernameNotFoundException(username));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
