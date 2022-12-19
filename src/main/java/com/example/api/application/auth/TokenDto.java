package com.example.api.application.auth;

import lombok.Data;

@Data
public class TokenDto {
    private String accessToken;
    private Long accessTokenExpireDate;


    public TokenDto(String accessToken, long accessTokenExpireDate) {
        this.accessToken = accessToken;
        this.accessTokenExpireDate = accessTokenExpireDate;
    }
}
