package com.group15.CreamCloneBackend.domain.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group15.CreamCloneBackend.domain.enduporder.EndUpOrder;
import com.group15.CreamCloneBackend.domain.order.Order;
import com.group15.CreamCloneBackend.domain.user.UserShoes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Shoes {
    @Id
    @GeneratedValue
    private Long id;

    private String brand;

    private String image;

    private String priceOriginal;

    private String modelNumber;

    private String modelName;

    private String releaseDate;

    private Long bookmarkCnt=0L;

    @JsonIgnore
    @OneToMany(mappedBy = "shoes")
    private List<Order> inTradeList;


    @OneToMany(mappedBy = "shoes", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EndUpOrder> endUpTradeList;

    @OneToMany(mappedBy = "shoes", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserShoes> likedUserList;


    public Shoes(ProductDto productDto){
        this.bookmarkCnt = 0L;
        this.image = productDto.getImage();
        this.brand = productDto.getBrand();
        this.modelNumber = productDto.getModelNumber();
        this.modelName = productDto.getModelName();
        this.priceOriginal = productDto.getPriceOriginal();
        this.releaseDate = productDto.getReleaseDate();
    }


}
