package com.group15.CreamCloneBackend.domain.enduporder;

import com.group15.CreamCloneBackend.domain.product.Shoes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EndUpOrder {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shoes_id")
    private Shoes shoes;

    private Long matchingPrice;

    private String buyer;

    private String seller;

    private void setShoes(Shoes shoes) {
        this.shoes = shoes;
    }

    private void setMatchingPrice(Long matchingPrice) {
        this.matchingPrice = matchingPrice;
    }

    private void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    private void setSeller(String seller) {
        this.seller = seller;
    }

    public static EndUpOrder createEndUpOrder(Shoes shoes, Long matchingPrice, String buyer, String seller) {
        EndUpOrder endUpOrder = new EndUpOrder();
        endUpOrder.setShoes(shoes);
        endUpOrder.setBuyer(buyer);
        endUpOrder.setSeller(seller);
        endUpOrder.setMatchingPrice(matchingPrice);
        return endUpOrder;
    }
}
