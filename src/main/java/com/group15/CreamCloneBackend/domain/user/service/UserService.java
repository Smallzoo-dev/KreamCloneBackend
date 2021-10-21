package com.group15.CreamCloneBackend.domain.user.service;

import com.group15.CreamCloneBackend.domain.product.Shoes;
import com.group15.CreamCloneBackend.domain.user.User;
import com.group15.CreamCloneBackend.domain.user.dto.BookmarkRequestDto;
import com.group15.CreamCloneBackend.domain.user.dto.UserRequestDto;
import com.group15.CreamCloneBackend.domain.user.dto.UserResponseDto;

import java.util.List;

public interface UserService {

    UserResponseDto usersignup(UserRequestDto userRequestDto);

    UserResponseDto userlogin(UserRequestDto userRequestDto);

    UserResponseDto bookmark(User user, BookmarkRequestDto bookmark);

    List<Shoes> getBookmarkList(User user);

    void passwordCheck(String password);
}
