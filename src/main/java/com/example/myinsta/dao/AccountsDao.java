package com.example.myinsta.dao;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class AccountsDao {

    private long idAccount;

    private String email;

    private String nickName;

    private String password;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

}
