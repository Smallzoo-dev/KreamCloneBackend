package com.group15.CreamCloneBackend.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MypageResponseDto {

    private List<mypageOrderDto> buyList;
    private List<mypageOrderDto> sellList;
    private List<bookmarkListDto> bookmarkList;

    private Long statusCode;
    private String msg;
    private String userName;


    public MypageResponseDto(List<mypageOrderDto> buyList, List<mypageOrderDto> sellList, List<bookmarkListDto> bookmarkList) {
        this.buyList = buyList;
        this.sellList = sellList;
        this.bookmarkList = bookmarkList;
    }


}
