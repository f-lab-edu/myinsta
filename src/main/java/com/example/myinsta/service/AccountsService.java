package com.example.myinsta.service;

import com.example.myinsta.dto.AccountsInsertTestDto;
import com.example.myinsta.mapper.AccountsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountsService {
    private final AccountsMapper accountsMapper;
    public int insertTest(AccountsInsertTestDto accountsInsertTestDto){
        return accountsMapper.insertTest( accountsInsertTestDto );
    }
}
