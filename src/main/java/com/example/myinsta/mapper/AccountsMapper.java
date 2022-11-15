package com.example.myinsta.mapper;


import com.example.myinsta.dto.AccountsInsertTestDto;
import com.example.myinsta.dto.AccountsSelectTestDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AccountsMapper {
    //Mapping a SQL query with MyBatis
    int insertTest(@Param("accountsInsertTestDto") AccountsInsertTestDto accountsInsertTestDto);

    List<AccountsSelectTestDto> selectTest();
}
