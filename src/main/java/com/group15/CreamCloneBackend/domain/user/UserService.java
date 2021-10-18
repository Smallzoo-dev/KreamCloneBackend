package com.group15.CreamCloneBackend.domain.user;

import com.group15.CreamCloneBackend.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public void usersignup(UserRequestDto userRequestDto){

        String encodingPw = passwordEncoder.encode(userRequestDto.getPassword());
        User user = new User(userRequestDto.getUsername(),encodingPw);

        userRepository.save(user);

    }

    public  UserResponseDto userlogin(UserRequestDto userRequestDto){

        Optional<User> user = userRepository.findByUsername(userRequestDto.getUsername());

        if (!user.isPresent()){

            return new UserResponseDto(StatusCode.STATUS_FAILE,ResponseMsg.MSG_FAILE_LOGIN_USERNAME);

        }else if (!user.get().getPassword().equals(userRequestDto.getPassword())){

            return new UserResponseDto(StatusCode.STATUS_FAILE,ResponseMsg.MSG_FAILE_LOGIN_PASSWORD);

        }
        String token = jwtTokenProvider.createToken(userRequestDto.getUsername());

        return new UserResponseDto(StatusCode.STATUS_SUCCESS,ResponseMsg.MSG_SUCCESS_LOGIN,token);

    }
}
