package com.group15.CreamCloneBackend.domain.product.dto;

import com.group15.CreamCloneBackend.domain.product.Shoes;
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
    private String modelNumber;
    private Boolean isOriginPrice = true;


   public MainDto(Shoes shoes){
        this.id = shoes.getId();
        this.brand = shoes.getBrand();
        this.modelName = shoes.getModelName();
        this.image = shoes.getImage();
        this.modelNumber = shoes.getModelNumber();
        this.price = shoes.getPriceOriginal();
    }
}

