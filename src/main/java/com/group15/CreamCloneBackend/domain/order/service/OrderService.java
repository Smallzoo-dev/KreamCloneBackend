package com.group15.CreamCloneBackend.domain.order.service;

import com.group15.CreamCloneBackend.domain.order.Order;
import com.group15.CreamCloneBackend.domain.order.TradeType;
import com.group15.CreamCloneBackend.domain.order.TradingRole;

public interface OrderService {


    public Long order(Long memberId, Long shoesId, TradingRole tradingRole, TradeType tradeType, String shoeSize, Long price);

    public Long buyOrdercreate(Long memberId, Long shoesId, TradingRole tradingRole, String shoesSize, Long price);

    public Long buyOrdermatch(Long shoesId, Long userId, Long price);

    public Long sellOrdercreate(Long memberId, Long shoesId, TradingRole tradingRole, String shoesSize, Long price);

    public Long sellOrdermatch(Long shoesId, Long userId, Long price);
}
