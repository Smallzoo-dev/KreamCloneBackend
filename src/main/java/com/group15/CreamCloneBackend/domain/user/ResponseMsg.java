package com.group15.CreamCloneBackend.domain.user;

public enum ResponseMsg {

    MSG_SUCCESS_SIGNUP("회원가입 성공"),
    MSG_FAILE_SIGNUP("회원가입 실패"),
    MSG_FAILE_LOGIN_USERNAME("로그인 실패 : 해당하는 사용자가 없습니다"),
    MSG_FAILE_LOGIN_PASSWORD("로그인 실패 : 비밀번호를 확인해주세요"),
    MSG_SUCCESS_LOGIN("로그인 성공"),
    MSG_IS_USER("회원입니다"),
    MSG_IS_ANONYMOUS("회원이 아닙니다");

    private String msg;

    ResponseMsg(String msg){
        this.msg = msg;
    }
}
