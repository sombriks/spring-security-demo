package com.example.demo;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthCtl {

    private final AuthService authService;

    public AuthCtl(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public String auth(@RequestBody LoginDTO login) {
        return authService.getToken(login);
    }
}
