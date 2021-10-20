package com.group15.CreamCloneBackend.domain.product.dto;

import com.group15.CreamCloneBackend.domain.product.Shoes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MainDto {
    private String brand;
    private String modelName;
    private String image;
    private Boolean bookMark;
    private Long price;
    private Long productId;


   public MainDto(Shoes shoes){
        this.brand = shoes.getBrand();
        this.modelName = shoes.getModelName();
        this.image = shoes.getImage();
        this.bookMark = shoes.getBookMark();
        this.price = shoes.getPrice();
        this.productId = shoes.getProductId();
    }
}

