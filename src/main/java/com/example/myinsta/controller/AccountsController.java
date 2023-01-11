package com.example.myinsta.controller;

import com.example.myinsta.dto.RequestLoginDto;
import com.example.myinsta.dto.RequestSignUpDto;
import com.example.myinsta.service.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/accounts", consumes = "application/json")
public class AccountsController {
    private final AccountsService accountsService;
    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@Valid @RequestBody RequestSignUpDto requestSignUpDto) {
        accountsService.signUp(requestSignUpDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid RequestLoginDto requestLoginDto, HttpSession session){
        accountsService.login(requestLoginDto.getEmail(), requestLoginDto.getPassword());
        session.setAttribute("account",requestLoginDto.getEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/logout")
    public ResponseEntity<Object> logout(HttpSession session){
        session.removeAttribute("account");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
