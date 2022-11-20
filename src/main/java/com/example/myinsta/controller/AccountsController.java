package com.example.myinsta.controller;

import com.example.myinsta.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountsController {

    @RequestMapping("/signUp")
    public int signUp(@RequestBody SignUpDto signUpDto){
        log.info( signUpDto.getEmail(), signUpDto.getNick_name(), signUpDto.getPassword() );
        return 1;
    }

}
