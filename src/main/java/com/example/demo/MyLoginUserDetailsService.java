package com.example.demo;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyLoginUserDetailsService implements UserDetailsService {

    private final MyLoginRepository repository;

    public MyLoginUserDetailsService(MyLoginRepository repository){
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // create a custom call on your repository to return MyLogin by username
        // Optional<MyLogin> getByLogin(String login);
        return repository
                .getByLogin(username)
                .map(UserDetailsDTO::new)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }
}
