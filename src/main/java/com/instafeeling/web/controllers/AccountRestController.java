package com.instafeeling.web.controllers;

import com.instafeeling.domain.services.AccountManagerService;
import com.instafeeling.web.api_docs.custom_responses.StandardErrorResponses;
import com.instafeeling.web.dtos.UpdateEmailDTO;
import com.instafeeling.web.dtos.UpdatePasswordDTO;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/v1/accounts")
@AllArgsConstructor
public class AccountRestController {
    private final AccountManagerService accountManagerService;

    @StandardErrorResponses
    @ApiResponse(responseCode = "200", description = "Email updated")
    @PatchMapping("/update-email")
    public ResponseEntity<Void> updateEmail(@Valid @RequestBody UpdateEmailDTO updateEmailDTO, @AuthenticationPrincipal Long userId){
        this.accountManagerService.updateEmail(userId, updateEmailDTO.email(), updateEmailDTO.password());
        return ResponseEntity.ok().build();
    }

    @StandardErrorResponses
    @ApiResponse(responseCode = "200", description = "Password Updated")
    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(@Valid @RequestBody UpdatePasswordDTO updateEmailDTO, @AuthenticationPrincipal Long userId){
        this.accountManagerService.updatePassword(userId, updateEmailDTO.currentPassword(), updateEmailDTO.newPassword());
        return ResponseEntity.ok().build();
    }
}
