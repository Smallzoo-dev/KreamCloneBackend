package com.group15.CreamCloneBackend.domain.user.service;

import com.group15.CreamCloneBackend.domain.product.Shoes;
import com.group15.CreamCloneBackend.domain.product.repository.ShoesRepository;
import com.group15.CreamCloneBackend.domain.user.*;
import com.group15.CreamCloneBackend.domain.user.Enum.ResponseMsg;
import com.group15.CreamCloneBackend.domain.user.Enum.StatusCode;
import com.group15.CreamCloneBackend.domain.user.dto.UserRequestDto;
import com.group15.CreamCloneBackend.domain.user.dto.UserResponseDto;
import com.group15.CreamCloneBackend.domain.user.repository.UserRepository;
import com.group15.CreamCloneBackend.domain.user.repository.UserShoesRepository;
import com.group15.CreamCloneBackend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private final JwtTokenProvider jwtTokenProvider;
    private final UserShoesRepository userShoesRepository;
    private final ShoesRepository shoesRepository;



    //회원가입
    public UserResponseDto usersignup(UserRequestDto userRequestDto) {

        String encodingPw = passwordEncoder.encode(userRequestDto.getPassword());
        User user = new User(userRequestDto.getUsername(), encodingPw);

        userRepository.save(user);

        return new UserResponseDto(StatusCode.STATUS_SUCCESS.getStatusCode(), ResponseMsg.MSG_SUCCESS_SIGNUP.getMsg());

    }

    //로그인
    public UserResponseDto userlogin(UserRequestDto userRequestDto) {
        UserResponseDto responseDto;

        User user = userRepository.findByUsername(userRequestDto.getUsername());

        if (user==null) {

            responseDto = new UserResponseDto(StatusCode.STATUS_FAILE.getStatusCode(), ResponseMsg.MSG_FAILE_LOGIN_USERNAME.getMsg());

        } else if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {

            responseDto = new UserResponseDto(StatusCode.STATUS_FAILE.getStatusCode(), ResponseMsg.MSG_FAILE_LOGIN_PASSWORD.getMsg());

        } else {
            //로그인 성공 시
            String token = jwtTokenProvider.createToken(userRequestDto.getUsername());

            responseDto = new UserResponseDto(StatusCode.STATUS_SUCCESS.getStatusCode(), ResponseMsg.MSG_SUCCESS_LOGIN.getMsg(), token);
        }
        return responseDto;
    }

    //북마크
    @Transactional
    public UserResponseDto bookmark(User user, Long productId, Boolean bookmark){

        UserResponseDto userResponseDto;

        Optional<Shoes> shoes = shoesRepository.findById(productId);
        //신발 정보가 존재하는지 체크
        if (shoes.isEmpty()){

            userResponseDto = new UserResponseDto(StatusCode.STATUS_FAILE.getStatusCode(),ResponseMsg.MSG_NOFOUND_SHOES.getMsg() );

        }else {

            if (!bookmark){ //북마크가 되어있지 않을 때 북마크 추가
                shoes.get().setBookmarkCnt(shoes.get().getBookmarkCnt()+1L);
                Shoes shoes1 = shoes.get();
                UserShoes userShoes = new UserShoes(user,shoes1);
                userShoesRepository.save(userShoes);
                userResponseDto = new UserResponseDto(StatusCode.STATUS_SUCCESS.getStatusCode(),
                        ResponseMsg.MSG_FAILE_SIGNUP.getMsg(),
                        shoes1.getBookmarkCnt());
            }

            else { //북마크가 되어있을 때 북마크 삭제
                shoes.get().setBookmarkCnt(shoes.get().getBookmarkCnt()-1L);
                Shoes shoes1 = shoes.get();
                userShoesRepository.delete(userShoesRepository.findByUserAndShoes(user,shoes1));
                userResponseDto = new UserResponseDto(StatusCode.STATUS_SUCCESS.getStatusCode(),
                        ResponseMsg.MSG_SUCCESS_DEL_BOOKMARK.getMsg(),
                        shoes1.getBookmarkCnt());
            }

        }
        return userResponseDto;
    }
}

