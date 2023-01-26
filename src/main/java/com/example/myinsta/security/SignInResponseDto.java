package com.example.myinsta.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class SignInResponseDto {
    private String grantType;
    private String accessToken;
}
