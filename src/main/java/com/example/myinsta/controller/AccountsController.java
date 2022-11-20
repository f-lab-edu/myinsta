package com.example.myinsta.controller;

import com.example.myinsta.dto.SignUpDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

//@RestController : @Controller + @ResponseBody, it response with Json data.
//@RequiredArgsConstructor : Lombok annotation which makes Constructor with all the member fields as arguments.
//@Slf4j : To use logger for debugging purpose.
@RestController
@RequiredArgsConstructor
@Slf4j
public class AccountsController {


    //@RequestMapping : Mapping POST request with "/signUp" url with signUp() endpoint.
    @RequestMapping("/signUp")
    //@Valid : Using Java Bean Validation to validate the user input
    //@RequestBody : To bind user input to SignUpDto
    public int signUp(@RequestBody @Valid SignUpDto signUpDto){
        //log.debug() : To see what data is being bound to SignUpDto
        log.debug( "email : {} \n nick_name : {} \n password : {}" ,signUpDto.getEmail() , signUpDto.getNick_name() , signUpDto.getPassword() );
        //redirect to the root page.
        return 1;
    }
}
