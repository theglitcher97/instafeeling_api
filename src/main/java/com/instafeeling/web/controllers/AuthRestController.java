package com.instafeeling.web.controllers;

import com.instafeeling.web.dtos.AuthDTO;
import com.instafeeling.web.dtos.LoginDTO;
import com.instafeeling.web.dtos.SignUpDTO;
import com.instafeeling.domain.services.AuthService;
import com.instafeeling.web.utils.JwtUtils;
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
    private final JwtUtils jwtUtils;


    @PostMapping("/signup")
    public ResponseEntity<AuthDTO> signUp(@Valid @RequestBody SignUpDTO signUpDTO){
        // check passwords are equal
        if (!signUpDTO.password().equals(signUpDTO.confirmPassword()))
            throw new RuntimeException("passwords don't coincide");

        Long id = this.authService.signup(signUpDTO.email(), signUpDTO.password());
        return new ResponseEntity<>(new AuthDTO(this.jwtUtils.createToken(id)), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthDTO> login(@Valid @RequestBody LoginDTO loginDTO){
        Long userId = this.authService.login(loginDTO.email(), loginDTO.password());
        return ResponseEntity.ok().body(new AuthDTO(this.jwtUtils.createToken(userId)));
    }
}
