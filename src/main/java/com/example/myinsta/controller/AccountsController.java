package com.example.myinsta.controller;

import com.example.myinsta.dto.AccountsInsertTestDto;
import com.example.myinsta.dto.AccountsSelectTestDto;
import com.example.myinsta.service.AccountsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//생성자 주입을 위해 사용
@RequiredArgsConstructor
public class AccountsController {
    private final AccountsService accountsService;

    @PostMapping("/accounts/test/insert")
    public int insertTest( @RequestBody AccountsInsertTestDto accountsInsertTestDto ){
        return accountsService.insertTest( accountsInsertTestDto );
    }

    @RequestMapping("/accounts/test/select")
    public List<AccountsSelectTestDto> selectTest(){
        return accountsService.selectTest();
    }
}
