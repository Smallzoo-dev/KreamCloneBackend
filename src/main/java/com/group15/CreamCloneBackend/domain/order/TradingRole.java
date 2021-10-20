package com.group15.CreamCloneBackend.domain.order;

public enum TradingRole {

    BUYER("buy"), SELLER("sell");

    private final String text;

    TradingRole(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static TradingRole fromString(String text) {
        for (TradingRole b : TradingRole.values()) {
            if (b.text.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}
