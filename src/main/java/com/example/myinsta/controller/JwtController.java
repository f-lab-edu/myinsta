package com.example.myinsta.controller;

import com.example.myinsta.service.JwtService;
import com.example.myinsta.dto.LoginDto;
import com.example.myinsta.dto.RequestSignUpDto;
import com.example.myinsta.dto.ResponseSignInDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
public class JwtController {
    private final JwtService jwtService;
    @PostMapping("/jwt/signUp")
    public ResponseEntity<Void> signUp(@Valid @RequestBody RequestSignUpDto requestSignUpDto) {
        jwtService.signUp(requestSignUpDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/jwt/signIn")
    public ResponseEntity<ResponseSignInDto> signIn(@Valid @RequestBody LoginDto loginDTO) {
        ResponseSignInDto responseSignInDto = jwtService.login(loginDTO);
        return ResponseEntity.status(OK).body(responseSignInDto);
    }
}
