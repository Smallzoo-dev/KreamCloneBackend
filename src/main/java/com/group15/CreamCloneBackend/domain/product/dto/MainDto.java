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
    private Long id;
    private String brand;
    private String modelName;
    private String image;
    private Boolean bookMark = false;
    private String price;
    private Long productId;


   public MainDto(Shoes shoes){
        this.id = shoes.getId();
        this.brand = shoes.getBrand();
        this.modelName = shoes.getModelName();
        this.image = shoes.getImage();
        this.productId = shoes.getProductId();
        this.price = shoes.getPriceOriginal();
    }
}

