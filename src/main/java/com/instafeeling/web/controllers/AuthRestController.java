package com.instafeeling.web.controllers;

import com.instafeeling.domain.dtos.LoginDTO;
import com.instafeeling.domain.dtos.SignUpDTO;
import com.instafeeling.domain.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/auth")
@AllArgsConstructor
public class AuthRestController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpDTO signUpDTO){
        this.authService.signup(signUpDTO);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginDTO loginDTO){
        this.authService.login(loginDTO);
        return ResponseEntity.ok().build();
    }
}
