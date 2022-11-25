package com.example.myinsta.controller;

import com.example.myinsta.dto.TestDto;
import com.example.myinsta.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;

    @PostMapping("/hello")
    public String hello(TestDto testDto){
        return testService.hello();
    }
}
