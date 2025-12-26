package com.instafeeling.web.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePasswordDTO(
        @NotBlank(message = "please provide a new password")
        @Size(min = 8, max = 255, message = "the password must be between 8 and 255 characters long")
        String newPassword,

        @NotBlank(message = "please provide you current password")
        String currentPassword
) {
}
