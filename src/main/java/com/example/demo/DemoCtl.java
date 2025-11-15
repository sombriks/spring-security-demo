package com.example.demo;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoCtl {

    @GetMapping
    public String index() {
        return "Hello stranger!";
    }

    @GetMapping("protected")
    public String authenticated(@AuthenticationPrincipal UserDetails userDetails) {
        return "Hello, " + userDetails.getUsername();
    }

    @GetMapping("admin")
    @PreAuthorize("hasAuthority('ADM')")
    public String admin(@AuthenticationPrincipal UserDetails userDetails) {
        return "Hello, admin " + userDetails.getUsername();
    }
}
