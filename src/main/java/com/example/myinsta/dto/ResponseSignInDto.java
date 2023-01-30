package com.example.myinsta.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class ResponseSignInDto {
    private String grantType;
    private String accessToken;
}
