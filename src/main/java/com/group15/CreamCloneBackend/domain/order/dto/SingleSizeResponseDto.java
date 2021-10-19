package com.group15.CreamCloneBackend.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleSizeResponseDto {
    private String priceRecent;
    private String priceBuy;
    private String priceSell;
    private Long statusCode;
    private String msg;
}
