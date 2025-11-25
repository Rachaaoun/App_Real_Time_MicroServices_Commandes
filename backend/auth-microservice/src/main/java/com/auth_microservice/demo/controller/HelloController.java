package com.auth_microservice.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/secure/hello")
    public String secureHello() {
        return "Hello, you are authenticated!";
    }

    @GetMapping("/public/hello")
    public String publicHello() {
        return "Hello, this is public!";
    }
}
