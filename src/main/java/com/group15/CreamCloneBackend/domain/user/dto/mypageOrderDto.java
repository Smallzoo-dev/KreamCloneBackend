package com.group15.CreamCloneBackend.domain.user.dto;

import com.group15.CreamCloneBackend.domain.order.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DecimalFormat;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class mypageOrderDto {
    private String modelName;
    private String transactionPrice;
    private String purchaseType = "입찰 거래";


    public mypageOrderDto(Order order) {
        DecimalFormat decFormat = new DecimalFormat("###,###");
        this.modelName = order.getShoes().getModelName();
        this.transactionPrice = decFormat.format(order.getPrice());
    }
}
