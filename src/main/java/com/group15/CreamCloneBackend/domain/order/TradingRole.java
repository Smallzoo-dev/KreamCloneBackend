package com.group15.CreamCloneBackend.domain.order;

public enum TradingRole {

    BUYER("buy"), SELLER("sell");

    private final String tradingRole;

    TradingRole(String tradingRole) {
        this.tradingRole = tradingRole;
    }
}
