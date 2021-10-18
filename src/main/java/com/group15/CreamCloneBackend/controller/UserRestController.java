package com.group15.CreamCloneBackend.controller;

import com.group15.CreamCloneBackend.domain.user.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UserRestController {
    UserService userService;
    UserRepository userRepository;



    //회원가입
    @ApiOperation(value = "회원가입",notes = "회원가입")
    @PostMapping("/user/signup")
    public UserResponseDto userSignup(@RequestBody UserRequestDto userRequestDto){
        userService.usersignup(userRequestDto);
        return new UserResponseDto(StatusCode.STATUS_SUCCESS,ResponseMsg.MSG_SUCCESS_SIGNUP);

    }

    //로그인
    @ApiOperation(value = "로그인",notes = "로그인")
    @PostMapping("/user/login")
    public UserResponseDto userLogin(@RequestBody UserRequestDto userRequestDto){
        return userService.userlogin(userRequestDto);

    }


}
