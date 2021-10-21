package com.group15.CreamCloneBackend.domain.user.service;

import com.group15.CreamCloneBackend.domain.order.repository.OrderRepository;
import com.group15.CreamCloneBackend.domain.product.Shoes;
import com.group15.CreamCloneBackend.domain.product.repository.ShoesRepository;
import com.group15.CreamCloneBackend.domain.user.*;
import com.group15.CreamCloneBackend.domain.user.Enum.ResponseMsg;
import com.group15.CreamCloneBackend.domain.user.Enum.StatusCode;
import com.group15.CreamCloneBackend.domain.user.dto.BookmarkRequestDto;
import com.group15.CreamCloneBackend.domain.user.dto.UserRequestDto;
import com.group15.CreamCloneBackend.domain.user.dto.UserResponseDto;
import com.group15.CreamCloneBackend.domain.user.repository.UserRepository;
import com.group15.CreamCloneBackend.domain.user.repository.UserShoesRepository;
import com.group15.CreamCloneBackend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserShoesRepository userShoesRepository;
    private final ShoesRepository shoesRepository;
    private final OrderRepository orderRepository;


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
    public UserResponseDto bookmark(User user, BookmarkRequestDto bookmark){

        UserResponseDto userResponseDto;

        Optional<Shoes> shoes = shoesRepository.findById(bookmark.getProductId());
        System.out.println(shoes.get().getProductId());
        //신발 정보가 존재하는지 체크
        if (!shoes.isPresent()){

            userResponseDto = new UserResponseDto(StatusCode.STATUS_FAILE.getStatusCode(),ResponseMsg.MSG_NOFOUND_SHOES.getMsg() );

        }else {

            if (!bookmark.getBookmark()){ //북마크가 되어있지 않을 때 북마크 추가
                shoes.get().setBookmarkCnt(shoes.get().getBookmarkCnt()+1L);
                Shoes shoes1 = shoes.get();
                UserShoes userShoes = new UserShoes(user,shoes1);
                userShoesRepository.save(userShoes);
                userResponseDto = new UserResponseDto(StatusCode.STATUS_SUCCESS.getStatusCode(),
                        ResponseMsg.MSG_SUCCESS_BOOKMARK.getMsg(),
                        shoes1.getBookmarkCnt());
            }

            else { //북마크가 되어있을 때 북마크 삭제
                shoes.get().setBookmarkCnt(shoes.get().getBookmarkCnt()-1L);
                Shoes shoes1 = shoes.get();
                UserShoes userShoes = userShoesRepository.findByUserAndShoes(user,shoes1);

                userShoesRepository.delete(userShoes);
                userResponseDto = new UserResponseDto(StatusCode.STATUS_SUCCESS.getStatusCode(),
                        ResponseMsg.MSG_SUCCESS_DEL_BOOKMARK.getMsg(),
                        shoes1.getBookmarkCnt());
            }

        }
        return userResponseDto;
    }


    //북마크 리스트 가져오기
    public List<Shoes> getBookmarkList(User user){

        List<UserShoes> userShoesList = userShoesRepository.findAllByUser(user);
        List<Shoes> shoesList = new ArrayList<>();

        for (UserShoes userShoes : userShoesList) {
            Shoes shoes =userShoes.getShoes();
            shoesList.add(shoes);
       }
        return shoesList;

    }

    public void passwordCheck(String password) {
        final int MIN = 8;
        final int MAX = 16;
        final String pattern = "^((?=.*[0-9a-zA-Z!@#$%^&*])(?=.*[\\W]).{" + MIN + "," + MAX + "})$";
        if (!Pattern.matches(pattern, password)) {
            throw new IllegalArgumentException("비밀번호 입력 오류");
        }
    }

    public void usernameCheck(String username) {
        final String pattern = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
        if (!Pattern.matches(pattern, username)) {
            throw new IllegalArgumentException("올바른 이메일 형식이 아닙니다.");
        }
        User found = userRepository.findByUsername(username);
        if (found!=null) {
            throw new IllegalArgumentException("중복 이메일 오류.");
        }
    }
}


