package com.group15.CreamCloneBackend.domain.user.dto;

import lombok.Getter;

@Getter
public class UserResponseDto {

    private final String statusCode;
    private final String responseMsg;
    private String token;
    private Long bookmarkCnt;
    private Object object;

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
    //상세페이지 북마크
    public UserResponseDto(String statusCode, String msg, Long bookmarkCnt) {
        this.statusCode=statusCode;
        this.responseMsg=msg;
        this.bookmarkCnt=bookmarkCnt;
    }

    //상세페이지 북마크
    public UserResponseDto(String statusCode, String msg, OrderListDto mypageResponseDto) {
        this.statusCode=statusCode;
        this.responseMsg=msg;
        this.object = mypageResponseDto;
    }

}
