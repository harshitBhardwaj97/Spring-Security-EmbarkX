package com.springsecurity.embarkx.Spring_Security_EmbarkX;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/helloUser")
    public String helloUser() {
        return "Hello, user";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/helloAdmin")
    public String helloAdmin() {
        return "Hello, admin";
    }
}
