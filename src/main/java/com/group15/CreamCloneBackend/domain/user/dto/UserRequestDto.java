package com.group15.CreamCloneBackend.domain.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
public class UserRequestDto {

    private final String username;
    private final String password;

    UserRequestDto(String username, String password){
        this.username=username;
        this.password=password;
    }
}
