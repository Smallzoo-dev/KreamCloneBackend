package com.group15.CreamCloneBackend.controller;

import com.group15.CreamCloneBackend.domain.user.*;
import com.group15.CreamCloneBackend.security.UserDetailsImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class UserRestController {
    UserService userService;

    //회원가입
    @ApiOperation(value = "회원가입",notes = "회원가입")
    @PostMapping("/user/signup")
    public UserResponseDto userSignup(@RequestBody UserRequestDto userRequestDto){
        userService.usersignup(userRequestDto);
        return new UserResponseDto(StatusCode.STATUS_SUCCESS,ResponseMsg.MSG_SUCCESS_SIGNUP);

    }

    //로그인
    @ApiOperation(value = "로그인",notes = "상태코드, 메시지, 토큰값")
    @PostMapping("/user/login")
    public UserResponseDto userLogin(@RequestBody UserRequestDto userRequestDto){
        return userService.userlogin(userRequestDto);

    }

    //로그인체크
    @ApiOperation(value = "로그인 체크",notes = "상태 코드값, 메시지")
    @GetMapping("/user/logincheck")
    public UserResponseDto userLoginCheck(@AuthenticationPrincipal UserDetailsImpl userDetails){
        if (userDetails==null){
            return new UserResponseDto(StatusCode.STATUS_FAILE,ResponseMsg.MSG_IS_ANONYMOUS);
        }
        return new UserResponseDto(StatusCode.STATUS_SUCCESS,ResponseMsg.MSG_IS_USER);

    }

}
