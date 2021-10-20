package com.group15.CreamCloneBackend.domain.user.dto;

import com.group15.CreamCloneBackend.domain.product.Shoes;
import lombok.Getter;

import java.util.List;

@Getter
public class BookmarkListDto {

    private OrderListDto orderListDto;
    private List<Shoes> likeShoesList;
    private UserResponseDto responseDto;

    public BookmarkListDto(OrderListDto orderListDto,List<Shoes> likeShoesList,UserResponseDto responseDto){
        this.likeShoesList=likeShoesList;
        this.orderListDto = orderListDto;
        this.responseDto = responseDto;
    }


}
