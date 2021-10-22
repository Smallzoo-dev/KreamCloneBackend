package com.group15.CreamCloneBackend.domain.user.dto;

import com.group15.CreamCloneBackend.domain.order.Order;
import lombok.Getter;
import java.util.List;

@Getter
public class OrderListDto {

    private List<Order> buyList;
    private List<Order> sellList;


    public OrderListDto(List<Order> buy, List<Order> sell){
        this.buyList = buy;
        this.sellList = sell;
    }



}
