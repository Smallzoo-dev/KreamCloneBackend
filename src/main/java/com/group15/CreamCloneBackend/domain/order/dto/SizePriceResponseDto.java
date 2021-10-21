package com.group15.CreamCloneBackend.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SizePriceResponseDto {
    private com.group15.CreamCloneBackend.domain.order.dto.priceBuy priceBuy;
    private Long statusCode;
    private String msg;
}
