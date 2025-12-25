package com.instafeeling.web.controllers;

import com.instafeeling.domain.services.AccountManagerService;
import com.instafeeling.web.dtos.UpdateEmailDTO;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1/accounts")
@AllArgsConstructor
public class AccountRestController {
    private final AccountManagerService accountManagerService;

    @PatchMapping("/update-email")
    public ResponseEntity<Void> updateEmail(@Valid @RequestBody UpdateEmailDTO updateEmailDTO){
        this.accountManagerService.updateEmail(updateEmailDTO);
        return ResponseEntity.ok().build();
    }
}
