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

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class UserRestController {
    private final UserService userService;

    //회원가입
    @ApiOperation(value = "회원가입",notes = "회원가입")
    @PostMapping("/user/signup")
    public UserResponseDto userSignup(@RequestBody UserRequestDto userRequestDto){

        return userService.usersignup(userRequestDto);

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
            return new UserResponseDto(StatusCode.STATUS_FAILE.getStatusCode(),ResponseMsg.MSG_IS_ANONYMOUS.getMsg());
        }
        return new UserResponseDto(StatusCode.STATUS_SUCCESS.getStatusCode(),ResponseMsg.MSG_IS_USER.getMsg());

    }


    //북마크
    @ApiOperation(value = "북마크",notes = "북마크 여부 리턴- 북마크 성공:200 해제:500")
    @PostMapping("/user/login")
    public UserResponseDto bookmark(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @RequestBody Long productId ){
        return userService.bookmark(userDetails.getUser(),productId);

    }


}
