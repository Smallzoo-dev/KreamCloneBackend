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
public class Order {

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





}
