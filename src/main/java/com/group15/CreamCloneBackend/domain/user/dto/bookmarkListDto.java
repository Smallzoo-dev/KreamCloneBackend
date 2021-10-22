package com.group15.CreamCloneBackend.domain.user.dto;

import com.group15.CreamCloneBackend.domain.product.Shoes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class bookmarkListDto {
    public String brand;
    public String modelName;
    public String originPrice;
    public String image;

    public bookmarkListDto(Shoes shoes) {
        this.brand = shoes.getBrand();
        this.modelName = shoes.getModelName();
        this.originPrice = shoes.getPriceOriginal();
        this.image = shoes.getImage();
    }
}
