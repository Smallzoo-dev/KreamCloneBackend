package com.group15.CreamCloneBackend.domain.user.service;

import com.group15.CreamCloneBackend.domain.user.User;
import com.group15.CreamCloneBackend.domain.user.dto.UserRequestDto;
import com.group15.CreamCloneBackend.domain.user.dto.UserResponseDto;

public interface UserService {

    UserResponseDto usersignup(UserRequestDto userRequestDto);

    UserResponseDto userlogin(UserRequestDto userRequestDto);

    UserResponseDto bookmark(User user, Long productId, Boolean bookmark);

}
