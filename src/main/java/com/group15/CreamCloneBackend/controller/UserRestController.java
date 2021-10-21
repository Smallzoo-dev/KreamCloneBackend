package com.group15.CreamCloneBackend.controller;

import com.group15.CreamCloneBackend.domain.product.Shoes;
import com.group15.CreamCloneBackend.domain.user.Enum.ResponseMsg;
import com.group15.CreamCloneBackend.domain.user.Enum.StatusCode;
import com.group15.CreamCloneBackend.domain.user.dto.*;
import com.group15.CreamCloneBackend.domain.user.service.MypageServiceImpl;
import com.group15.CreamCloneBackend.domain.user.service.UserServiceImpl;
import com.group15.CreamCloneBackend.security.UserDetailsImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class UserRestController {
    private final UserServiceImpl userService;
    private final MypageServiceImpl mypageService;


    //회원가입
    @ApiOperation(value = "회원가입",notes = "회원가입")
    @PostMapping("/user/signup")
    public UserResponseDto userSignup( @RequestBody UserRequestDto userRequestDto){
        userService.passwordCheck(userRequestDto.getPassword());
        userService.usernameCheck(userRequestDto.getUsername());

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
            return new UserResponseDto(StatusCode.STATUS_FAILE.getStatusCode(), ResponseMsg.MSG_IS_ANONYMOUS.getMsg());
        }
        return new UserResponseDto(StatusCode.STATUS_SUCCESS.getStatusCode(),ResponseMsg.MSG_IS_USER.getMsg());
    }


    //북마크
    @ApiOperation(value = "북마크",notes = "북마크 변경 성공 여부 리턴- 북마크 변경 성공:200 실패:500")
    @PostMapping("/user/bookmark")
    public UserResponseDto bookmark(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                    @RequestBody BookmarkRequestDto bookmarkRequestDto ) {
        return userService.bookmark(userDetails.getUser(), bookmarkRequestDto);
    }


    //마이페이지
    @ApiOperation(value = "마이페이지",notes = "구매목록, 판매목록, 북마크리스트, 상태코드")
    @GetMapping("/mypage")
    public BookmarkResponseDto getMypage(@AuthenticationPrincipal UserDetailsImpl userDetails){

        // 구매목록, 판매목록 담기
        OrderListDto OrderListDto = mypageService.getBuyAndSellList(userDetails.getUser());

        List<Shoes> likeShoesList = userService.getBookmarkList(userDetails.getUser());

        UserResponseDto responseDto = new UserResponseDto(StatusCode.STATUS_SUCCESS.getStatusCode(),
               ResponseMsg.MSG_LOAD_SUCCESS_MYPAGE.getMsg() );

         return new BookmarkResponseDto(OrderListDto,likeShoesList,responseDto);
    }
}
