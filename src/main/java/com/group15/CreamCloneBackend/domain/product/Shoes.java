package com.group15.CreamCloneBackend.domain.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group15.CreamCloneBackend.domain.enduporder.EndUpOrder;
import com.group15.CreamCloneBackend.domain.order.Order;
import com.group15.CreamCloneBackend.domain.user.UserShoes;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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

    private String ImgUrl;

    private String priceOriginal;

    private String modelName;

    private String releaseDate;


    private String modelNumber;

    private String image;

    private Long bookmarkCnt;

    public Shoes(ProductDto productDto){
        this.bookmarkCnt = 0L;
        this.image = productDto.getImage();
        this.brand = productDto.getBrand();
        this.modelNumber = productDto.getModelNumber();
        this.modelName = productDto.getModelName();
        this.priceOriginal = productDto.getPriceOriginal();
        this.releaseDate = productDto.getReleaseDate();
    }


    @JsonIgnore
    @OneToMany(mappedBy = "shoes")
    private List<Order> inTradeList;

    @OneToMany(mappedBy = "shoes", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EndUpOrder> endUpTradeList;

    @OneToMany(mappedBy = "shoes", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserShoes> likedUserList;


}
