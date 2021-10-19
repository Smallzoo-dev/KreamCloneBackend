package com.group15.CreamCloneBackend.domain.order;

public enum TradeType {
    Match("prompt"), Bidding("bidding");

    private final String requestParam;

    TradeType(String requestParam) {
        this.requestParam = requestParam;
    }


}
