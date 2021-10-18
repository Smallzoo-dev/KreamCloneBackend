package com.group15.CreamCloneBackend.domain.user;

import lombok.Getter;

@Getter
public class UserRequestDto {

    private final String username;
    private final String password;

    UserRequestDto(String username, String password){
        this.username=username;
        this.password=password;
    }
}
