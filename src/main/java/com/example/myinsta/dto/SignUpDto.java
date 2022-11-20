package com.example.myinsta.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SignUpDto {
    private String email;
    private String nick_name;
    private String password;
}
