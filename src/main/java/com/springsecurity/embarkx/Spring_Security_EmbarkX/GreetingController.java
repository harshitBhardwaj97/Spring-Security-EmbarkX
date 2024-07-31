package com.springsecurity.embarkx.Spring_Security_EmbarkX;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/hello")
    public String hello() {
        return "Hello";
    }
    
}
