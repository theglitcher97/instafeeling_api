package com.instafeeling.domain.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginDTO(
        @NotBlank(message = "please provide an email") // assert not null and not empty
        @Email(message = "please provide a valid email")
        String email,

        @NotBlank(message = "please provide a password")
        @Size(min = 8, max = 255, message = "the password must be between 8 and 255 characters long")
        String password
) {
}
