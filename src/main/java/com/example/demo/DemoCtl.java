package com.example.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoCtl {

    @GetMapping
    public String index() {
        return "Hello, stranger!";
    }

    @GetMapping("protected")
    public String authenticated(@AuthenticationPrincipal Jwt principal) {
        return String.format("Hello, %s!", principal.getSubject());
    }

    @GetMapping("admin")
    @PreAuthorize("authentication.principal.claims['scope'].contains('ADM')")
    public String admin(@AuthenticationPrincipal Jwt principal) {
        return String.format("Hello, admin %s!", principal.getSubject());
    }
}
