package com.group15.CreamCloneBackend.domain.user.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.constraints.AssertTrue;
import java.util.regex.Pattern;

@SpringBootTest
@ActiveProfiles("signupTest")
class UserServiceImplTest {

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
    final String pattern ="^((?=.*[0-9]+[a-z]+[A-Z]+[!@#$%^&*])(?=.*[\\W]).{" + 8 + "," + 16 + "})$";

    @Nested
    @DisplayName("password 검증")
    class PasswordValidation {

        @Nested
        @DisplayName("올바른 password")
        class PasswordAssertTrue {

            @Test
            @DisplayName("영문+숫자+특수문자 8~16")
            void passwordCheck1() {
                Assertions.assertTrue(Pattern.matches(pattern, pw1));
            }
            @Test
            @DisplayName("특수문자+영문+숫자 8~16")
            void passwordCheck2() {
                Assertions.assertTrue(Pattern.matches(pattern, pw2));
            }
        }
        @Nested
        @DisplayName("바르지 않은 password")
        class PasswordAssertFales {

            @Test
            @DisplayName("영문 8~16")
            void passwordCheck3() {
                Assertions.assertFalse(Pattern.matches(pattern, pw3));
            }

            @Test
            @DisplayName("숫자 8~16")
            void passwordCheck4() {
                Assertions.assertFalse(Pattern.matches(pattern, pw4));
            }

//            @Test
//            @DisplayName("특수문자 8~16")
//            void passwordCheck5() {
//                Assertions.assertFalse(Pattern.matches(pattern, pw5));
//            }

            @Test
            @DisplayName("영문+숫자+특수문자 6")
            void passwordCheck6() {
                Assertions.assertFalse(Pattern.matches(pattern, pw6));
            }
        }

    }
//    @Test
//    void usernameCheck() {
//    }
}