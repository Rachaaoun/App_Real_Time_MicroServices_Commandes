package com.auth_microservice.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/public/hello")
    public String publicHello(){
        return "Accessible à tous !";
    }

    @GetMapping("/secure/hello")
    public String secureHello(@AuthenticationPrincipal Jwt jwt){
        return "Hello " + jwt.getClaim("preferred_username");
    }
}
