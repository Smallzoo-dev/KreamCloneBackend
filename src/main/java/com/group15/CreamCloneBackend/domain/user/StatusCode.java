package com.group15.CreamCloneBackend.domain.user;

import lombok.Getter;

@Getter
public enum StatusCode {
    STATUS_SUCCESS("200"),
    STATUS_FAILE("500")
    ;

    private final String statusCode;



    StatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
}
