package com.group15.CreamCloneBackend.domain.order;

import com.group15.CreamCloneBackend.domain.product.Shoes;
import com.group15.CreamCloneBackend.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "order_")
@NoArgsConstructor
@Getter
@Setter
public class Order extends TimeStamped{

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shoes_id")
    private Shoes shoes;

    private String shoesSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private TradingRole tradingRole;

    private Long price;




//    public void setUser(User user) {
//        this.user = user;
//        user.getJoinedOrderList().add(this);
//    }
//
//    public void setShoes(Shoes shoes) {
//        this.shoes = shoes;
//        shoes.getInTradeList().add(this);
//    }

    public static Order createOrder(User user, Shoes shoes, TradingRole tradingRole, String shoesSize, Long price) {
        Order order = new Order();
        order.setUser(user);
        order.setShoes(shoes);
        order.setTradingRole(tradingRole);
        order.setShoesSize(shoesSize);
        order.setPrice(price);
        return order;
    }





}
