package com.group15.CreamCloneBackend.domain.user;

public class UserResponseDto {
    private StatusCode statusCode;
    private ResponseMsg responseMsg;
    private String token;

    public UserResponseDto(StatusCode statusCode, ResponseMsg msg) {
        this.statusCode=statusCode;
        this.responseMsg=msg;
    }

    public UserResponseDto(StatusCode statusCode, ResponseMsg msg, String token) {
        this.statusCode=statusCode;
        this.responseMsg=msg;
        this.token=token;
    }
}
