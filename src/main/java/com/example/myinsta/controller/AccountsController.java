package com.example.myinsta.controller;

import com.example.myinsta.dto.RequestSignUpDto;
import com.example.myinsta.service.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class AccountsController {
    private final AccountsService accountsService;
    @PostMapping("/accounts/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody RequestSignUpDto requestSignUpDto) {
        accountsService.signUp(requestSignUpDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
