package com.example.demo;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class AuthService {

    private final JwtEncoder jwtEncoder;
    private final PasswordEncoder passwordEncoder;
    private final MyLoginRepository myLoginRepository;

    public AuthService(
            MyLoginRepository myLoginRepository,
            PasswordEncoder passwordEncoder,
            JwtEncoder jwtEncoder) {
        this.myLoginRepository = myLoginRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }


    public String getToken(LoginDTO login) {
        // recover user and check if authentication matches
        MyLogin user = myLoginRepository
                .getByLogin(login.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(login.getUsername()));
        if (!passwordEncoder.matches(login.getPassword(), user.getPassword()))
            throw new UsernameNotFoundException(login.getUsername() + " not found");

        // now prepare to build the token
        Instant now = Instant.now();
        Instant exp = now.plus(1, ChronoUnit.DAYS);
        // mount scopes from GrantedAuthorities
        // a frontend app could make use of them
        String scope = user.getPerms()
                .replaceAll(";", " ");
        // JWT claims
        JwtClaimsSet claims = JwtClaimsSet
                .builder()
                .issuedAt(now)
                .expiresAt(exp)
                .issuer("example issuer")
                .subject(user.getEmail())
                .claim("scope", scope)
                .build();
        // finally return the token
        return jwtEncoder
                .encode(JwtEncoderParameters.from(claims))
                .getTokenValue();
    }
}
