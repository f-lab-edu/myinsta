package com.example.myinsta.dao;

import lombok.*;

import java.sql.Timestamp;

@Data
@Builder
public class AccountsDao {

    private long idAccount;

    private String email;

    private String nickName;

    private String password;

    private Timestamp createdDate;

    private Timestamp updatedDate;

}
