package com.example.myinsta.controller;

import com.example.myinsta.dto.SignUpDto;
import com.example.myinsta.service.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/accounts", consumes = "application/json")
public class AccountsController {
    private final AccountsService accountsService;
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody SignUpDto signUpDto) {
        accountsService.signUp(signUpDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
