package com.group15.CreamCloneBackend.domain.user;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private final String statusCode;
    private final String responseMsg;
    private String token;

    public UserResponseDto(String statusCode, String msg) {
        this.statusCode=statusCode;
        this.responseMsg=msg;
    }

    //로그인 시 발급되는 토큰값과 같이 응답
    public UserResponseDto(String statusCode, String msg, String token) {
        this.statusCode=statusCode;
        this.responseMsg=msg;
        this.token=token;
    }
}
