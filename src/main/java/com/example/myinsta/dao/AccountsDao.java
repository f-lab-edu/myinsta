package com.example.myinsta.dao;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class AccountsDao {
    private long id;
    private String email;
    private String nickName;
    private String password;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
