package com.group15.CreamCloneBackend.domain.order.service;

import com.group15.CreamCloneBackend.domain.order.Order;
import com.group15.CreamCloneBackend.domain.order.TradeType;
import com.group15.CreamCloneBackend.domain.order.TradingRole;
import com.group15.CreamCloneBackend.domain.order.dto.OrderResponseDto;
import com.group15.CreamCloneBackend.domain.order.dto.SingleSizeResponseDto;
import com.group15.CreamCloneBackend.domain.order.dto.SizePriceResponseDto;

public interface OrderService {


    public OrderResponseDto order(Long memberId, Long shoesId, TradingRole tradingRole, TradeType tradeType, String shoeSize, Long price);

    public Long buyOrdercreate(Long memberId, Long shoesId, TradingRole tradingRole, String shoesSize, Long price);

    public Long buyOrdermatch(Long shoesId, Long userId, Long price, String size);

    public Long sellOrdercreate(Long memberId, Long shoesId, TradingRole tradingRole, String shoesSize, Long price);

    public Long sellOrdermatch(Long shoesId, Long userId, String size , Long price);

    public SizePriceResponseDto getSizePrice(Long shoesId);

    public SingleSizeResponseDto getSinglePrice(Long shoesId, String shoesSize);
}
