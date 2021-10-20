package com.group15.CreamCloneBackend.domain.user.service;

import com.group15.CreamCloneBackend.domain.order.Order;
import com.group15.CreamCloneBackend.domain.order.TradingRole;
import com.group15.CreamCloneBackend.domain.order.repository.OrderRepository;
import com.group15.CreamCloneBackend.domain.user.dto.OrderListDto;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MypageServiceImpl implements MypageService{

    private final OrderRepository orderRepository;


    public OrderListDto getBuyAndSellList() {

        List<Order> allOrderList = orderRepository.findAll();
        List<Order> buyOrderList=new ArrayList<>();
        List<Order> sellOrderList=new ArrayList<>();

        for (Order order : allOrderList) {
            if (order.getTradingRole().equals(TradingRole.BUYER)){
                buyOrderList.add(order);
            }else{
                sellOrderList.add(order);
            }
        }

        return new OrderListDto(buyOrderList,sellOrderList);
    }
}
