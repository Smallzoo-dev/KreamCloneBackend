package com.group15.CreamCloneBackend.domain.user;


import lombok.Getter;


@Getter
public class UserRequestDto {

    private String username;
    private String password;
    private String token;


    UserRequestDto(String username, String password){
        this.username=username;
        this.password=password;
    }

}
