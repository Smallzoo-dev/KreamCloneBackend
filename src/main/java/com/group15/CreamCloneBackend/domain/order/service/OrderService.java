package com.group15.CreamCloneBackend.domain.order.service;

import com.group15.CreamCloneBackend.domain.order.Order;
import com.group15.CreamCloneBackend.domain.order.TradeType;
import com.group15.CreamCloneBackend.domain.order.TradingRole;

public interface OrderService {


    public Long order(Long memberId, Long shoesId, TradingRole tradingRole, TradeType tradeType, String shoeSize, Long price);

    public void buyOrdercreate();

    public void buyOrdermatch();

    public void sellOrdercreate();

    public void sellOrdermatch();
}
