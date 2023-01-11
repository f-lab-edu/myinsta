package com.example.myinsta.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestLoginDto {
    @Email(message = "Invalid Email format", regexp = ("^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$"))
    @NotNull
    private String email;
    @NotNull(message = "Passwords must not null")
    @Pattern(regexp = ("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#])[\\da-zA-Z!@#]{8,}$"), message = "Password must have at least 8 characters with maximum 16 characters, one Upper case, one number, one symbol.")
    private String password;
}
