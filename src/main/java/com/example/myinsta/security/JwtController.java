package com.example.myinsta.security;

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
    public ResponseEntity<SignInResponseDto> signIn(@Valid @RequestBody LoginDto loginDTO) {
        SignInResponseDto signInResponseDto = jwtService.login(loginDTO);
        return ResponseEntity.status(OK).body(signInResponseDto);
    }
}
