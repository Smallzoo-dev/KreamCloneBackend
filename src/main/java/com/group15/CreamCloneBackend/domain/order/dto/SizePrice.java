package com.group15.CreamCloneBackend.domain.order.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class SizePrice {
    private List<SizeEachPrice> sizeEachPriceList;
    private Long statusCode;
    private String msg;
}
