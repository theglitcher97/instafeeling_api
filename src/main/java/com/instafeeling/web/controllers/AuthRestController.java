package com.instafeeling.web.controllers;

import com.instafeeling.domain.dtos.LoginDTO;
import com.instafeeling.domain.dtos.SignUpDTO;
import com.instafeeling.domain.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> signUp(@Valid @RequestBody SignUpDTO signUpDTO){
        String jwt = this.authService.signup(signUpDTO);
        return new ResponseEntity<>(jwt, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginDTO loginDTO){
        String jwt = this.authService.login(loginDTO);
        return ResponseEntity.ok().body(jwt);
    }
}
