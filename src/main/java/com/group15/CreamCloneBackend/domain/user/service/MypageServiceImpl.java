package com.group15.CreamCloneBackend.domain.user.service;

import com.group15.CreamCloneBackend.domain.order.Order;
import com.group15.CreamCloneBackend.domain.order.TradingRole;
import com.group15.CreamCloneBackend.domain.order.repository.OrderRepository;
import com.group15.CreamCloneBackend.domain.user.User;
import com.group15.CreamCloneBackend.domain.user.UserShoes;
import com.group15.CreamCloneBackend.domain.user.dto.MypageResponseDto;
import com.group15.CreamCloneBackend.domain.user.dto.OrderListDto;

import com.group15.CreamCloneBackend.domain.user.dto.bookmarkListDto;
import com.group15.CreamCloneBackend.domain.user.dto.mypageOrderDto;
import com.group15.CreamCloneBackend.domain.user.repository.UserShoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.group15.CreamCloneBackend.domain.order.TradingRole.*;

@RequiredArgsConstructor
@Service
public class MypageServiceImpl implements MypageService{

    private final OrderRepository orderRepository;
    private final UserShoesRepository userShoesRepository;


    public OrderListDto getBuyAndSellList(User user) {

        List<Order> allOrderList = orderRepository.findAllByUser(user);
        List<Order> buyOrderList=new ArrayList<>();
        List<Order> sellOrderList=new ArrayList<>();

        for (Order order : allOrderList) {
            if (order.getTradingRole().equals(BUYER)){
                buyOrderList.add(order);
            }else{
                sellOrderList.add(order);
            }
        }
        return new OrderListDto(buyOrderList, sellOrderList);
    }

    public MypageResponseDto getMyPageData(User user) {
        List<mypageOrderDto> buyList = new ArrayList<>();
        List<mypageOrderDto> sellList = new ArrayList<>();
        List<bookmarkListDto> bookmarkList = new ArrayList<>();

        List<Order> buyOrders = orderRepository.findAllByTradingRoleAndUserOrderByCreatedAtAsc(SELLER, user);
        if (!buyOrders.isEmpty()) {
            for (Order order : buyOrders) {
                buyList.add(new mypageOrderDto(order));
            }
        }

        List<Order> sellOrders = orderRepository.findAllByTradingRoleAndUserOrderByCreatedAtAsc(BUYER, user);
        if (!sellList.isEmpty()) {
            for (Order sellOrder : sellOrders) {
                sellList.add(new mypageOrderDto(sellOrder));
            }
        }

        List<UserShoes> bookmarks = userShoesRepository.findAllByUser(user);
        if (!bookmarks.isEmpty()) {
            for (UserShoes bookmark : bookmarks) {
                bookmarkList.add(new bookmarkListDto(bookmark.getShoes()));
            }
        }
        return new MypageResponseDto(buyList, sellList, bookmarkList);

    }
}
