package com.example.demo;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final JwtEncoder jwtEncoder;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;

    public AuthService(
            UserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            JwtEncoder jwtEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }


    public String getToken(UserDetails login) {
        // recover user and check if authentication matches
        UserDetails user = userDetailsService
                .loadUserByUsername(login.getUsername());
        if (!passwordEncoder.matches(login.getPassword(), user.getPassword()))
            throw new UsernameNotFoundException(login.getUsername() + " not found");

        // now prepare to build the token
        Instant now = Instant.now();
        Instant exp = now.plus(1, ChronoUnit.DAYS);
        // mount scopes from GrantedAuthorities
        // a frontend app could make use of them
        String scope = user
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        // JWT claims
        JwtClaimsSet claims = JwtClaimsSet
                .builder()
                .issuedAt(now)
                .expiresAt(exp)
                .issuer("example issuer")
                .subject(user.getUsername())
                .claim("scope", scope)
                .build();
        // finally return the token
        return jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }
}
