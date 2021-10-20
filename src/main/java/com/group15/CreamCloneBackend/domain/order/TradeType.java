package com.group15.CreamCloneBackend.domain.order;

public enum TradeType {
    Match("prompt"), Bidding("bidding");

    private String text;

    TradeType(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static TradeType fromString(String text) {
        for (TradeType b : TradeType.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }


}
