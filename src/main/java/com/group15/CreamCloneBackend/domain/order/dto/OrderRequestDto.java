package com.group15.CreamCloneBackend.domain.order.dto;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {
    private String requestType;
    private String purchaseType;
    private Long priceExpected;
    private String size;

    public Boolean isNullRequest() {
        if (this.requestType == null || this.purchaseType == null || this.priceExpected == null || this.size == null) {
            return true;
        } else return false;
    }
}
