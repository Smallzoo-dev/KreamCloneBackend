package com.group15.CreamCloneBackend.domain.user.service;

import com.group15.CreamCloneBackend.domain.product.repository.ShoesRepository;
import com.group15.CreamCloneBackend.domain.user.Enum.ResponseMsg;
import com.group15.CreamCloneBackend.domain.user.Enum.StatusCode;
import com.group15.CreamCloneBackend.domain.user.User;
import com.group15.CreamCloneBackend.domain.user.dto.UserRequestDto;
import com.group15.CreamCloneBackend.domain.user.dto.UserResponseDto;
import com.group15.CreamCloneBackend.domain.user.repository.UserRepository;
import com.group15.CreamCloneBackend.domain.user.repository.UserShoesRepository;
import com.group15.CreamCloneBackend.security.JwtTokenProvider;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import java.util.regex.Pattern;


@SpringBootTest
@ActiveProfiles("signupTest")
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private UserShoesRepository userShoesRepository;
    @Mock
    private ShoesRepository shoesRepository;

    private UserService userService;

    @BeforeEach
    void init() {
        userService = new UserServiceImpl(passwordEncoder, userRepository, jwtTokenProvider, userShoesRepository, shoesRepository);

    }

    private String pw1 = "abc123!@#";
    private String pw2 = "!@#abc123";
    private String pw3 = "abcqwerty";
    private String pw4 = "124123234";
    private String pw5 = "@#$%$!@#";
    private String pw6 = "aa34$@";

    private String id1 = "email@abc.com";
    private String id2 = "email@abc.com";
    private String id3 = "EmaRil@abc.com";
    private String id4 = "email.abc@com";
    private String id5 = "emailabccom";
    private String id6 = "@emailabc.com";

    final String patternPw = "(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*()'/_=+{}-])[0-9a-zA-Z!@#$%^&*()'/_=+{}-]{8,16}$";

    final String patternId = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";


    @Nested
    @DisplayName("???????????? ??????")
    class SignupValidation {

        @Nested
        @DisplayName("password ??????")
        class PasswordValidation {

            @Nested
            @DisplayName("????????? password")
            class PasswordAssertTrue {

                @Test
                @DisplayName("??????+??????+???????????? 8~16")
                void passwordCheck1() {
                    //when
                    boolean a = Pattern.matches(patternPw, pw1);
                    //then
                    Assertions.assertTrue(a);
                }

                @Test
                @DisplayName("????????????+??????+?????? 8~16")
                void passwordCheck2() {
                    //when
                    boolean a = Pattern.matches(patternPw, pw2);
                    //then
                    Assertions.assertTrue(a);
                }
            }

            @Nested
            @DisplayName("????????? ?????? password")
            class PasswordAssertFales {

                @Test
                @DisplayName("?????? 8~16")
                void passwordCheck3() {
                    //when
                    boolean a = Pattern.matches(patternPw, pw3);
                    //then
                    Assertions.assertFalse(a);
                }

                @Test
                @DisplayName("?????? 8~16")
                void passwordCheck4() {
                    //when
                    boolean a = Pattern.matches(patternPw, pw4);
                    //then
                    Assertions.assertFalse(a);
                }

                @Test
                @DisplayName("???????????? 8~16")
                void passwordCheck5() {
                    //when
                    boolean a = Pattern.matches(patternPw, pw5);
                    //then
                    Assertions.assertFalse(a);
                }

                @Test
                @DisplayName("??????+??????+???????????? 6")
                void passwordCheck6() {
                    //when
                    boolean a = Pattern.matches(patternPw, pw6);
                    //then
                    Assertions.assertFalse(a);
                }
            }

        }

        @Nested
        @DisplayName("username ??????")
        class usernameVaildation {

            @Nested
            @DisplayName("????????? Id")
            class UsernameAssertTrue {

                @Test
                @DisplayName("?????? email ???")
                void UsernameCheck1() {
                    //when
                    boolean a = Pattern.matches(patternId, id1);
                    //then
                    Assertions.assertTrue(a);
                }
            }

            @Nested
            @DisplayName("???????????? ?????? Id ??????")
            class UsernameAssertFalse {

                @Test
                @DisplayName("????????? username")
                void UsernameCheck2() {

                    //when
                    User user = new User();
                    user.setUsername(id1);
                    user.setPassword(pw1);

                    User userF = new User();
                    userF.setUsername(id2);
                    userF.setPassword(pw1);

                    Mockito.when(userRepository.findByUsername(id2)).thenReturn(user);

                    //then
                    Assertions.assertThrows(IllegalArgumentException.class,
                            () -> userService.usernameCheck(id2), "?????? ????????? ??????.");

                }

                @Test
                @DisplayName("???????????? ????????? username")
                void UsernameCheck3() {
                    //when
                    boolean a = Pattern.matches(patternId, id3);
                    //then
                    Assertions.assertFalse(a);
                }

                @Test
                @DisplayName("@??? . ??? ????????? ?????? username")
                void UsernameCheck4() {
                    //when
                    boolean a = Pattern.matches(patternId, id4);
                    //then
                    Assertions.assertFalse(a);
                }

                @Test
                @DisplayName("@??? .??? ?????? username")
                void UsernameCheck5() {
                    //when
                    boolean a = Pattern.matches(patternId, id5);
                    //then
                    Assertions.assertFalse(a);
                }

                @Test
                @DisplayName("@??? ?????? ?????? username")
                void UsernameCheck6() {
                    //when
                    boolean a = Pattern.matches(patternId, id6);
                    //then
                    Assertions.assertFalse(a);
                }

            }

        }
    }

    @Nested
    @DisplayName("????????? ??????")
    class LoginValidation {

        @Nested
        @DisplayName("?????? ??????")
        class ????????????????????? {

            @Test
            @DisplayName("????????? ?????????")
            void ??????????????????() {
                //when
                Mockito.when(userRepository.findByUsername(id4)).thenReturn(null);
                UserRequestDto userRequestDto = new UserRequestDto(id5, pw1);
                UserResponseDto userResponseDto = new UserResponseDto(StatusCode.STATUS_FAILE.getStatusCode(),
                        ResponseMsg.MSG_FAILE_LOGIN_USERNAME.getMsg());

                //then
                Assertions.assertEquals(userResponseDto.getResponseMsg(), userService.userlogin(userRequestDto).getResponseMsg());

            }

            @Test
            @DisplayName("????????? ?????????")
            void ????????????????????????() {

                User user = new User(id1, passwordEncoder.encode(pw1));
                Mockito.when(userRepository.findByUsername(id1)).thenReturn(user);
                UserRequestDto userRequestDto = new UserRequestDto(id1, pw2);
                UserResponseDto userResponseDto = new UserResponseDto(StatusCode.STATUS_FAILE.getStatusCode(),
                        ResponseMsg.MSG_FAILE_LOGIN_PASSWORD.getMsg());

                Assertions.assertEquals(userService.userlogin(userRequestDto).getResponseMsg(), userResponseDto.getResponseMsg());

            }

        }

        @Nested
        @DisplayName("?????? ??????")
        class ????????????????????? {

            @Test
            @DisplayName("????????? ??????")
            void ???????????????() {

                User user = new User(id1, passwordEncoder.encode(pw1));
                UserRequestDto userRequestDto = new UserRequestDto(id1, pw1);
                Mockito.when(userRepository.findByUsername(id1)).thenReturn(user);
                Mockito.when(passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())).thenReturn(true);

                UserResponseDto userResponseDto = new UserResponseDto(StatusCode.STATUS_SUCCESS.getStatusCode(),
                        ResponseMsg.MSG_SUCCESS_LOGIN.getMsg());

                Assertions.assertEquals(userResponseDto.getResponseMsg(), userService.userlogin(userRequestDto).getResponseMsg());
            }

        }
    }

    @Nested
    @DisplayName("????????? ??????")
    class BookmarkValidation{
        //??????????????? ?????? ???


    }

}