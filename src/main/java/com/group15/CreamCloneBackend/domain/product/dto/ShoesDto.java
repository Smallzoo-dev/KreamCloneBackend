package com.group15.CreamCloneBackend.domain.product.dto;

import com.group15.CreamCloneBackend.domain.product.Shoes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class ShoesDto {
    private Long productId;
    private String brand;
    private String modelName;
    private String image;
    private Long bookMarkCnt;
    private String modelNumber;
    private String releaseDate;
    private String priceOriginal;
    private Long statusCode;
    private String msg;

    public ShoesDto(Shoes shoes) {
        this.productId = shoes.getId();
        this.brand = shoes.getBrand();
        this.modelName = shoes.getModelName();
        this.image = shoes.getImage();
        this.bookMarkCnt = shoes.getBookmarkCnt();
        this.modelNumber = shoes.getModelNumber();
        this.releaseDate = shoes.getReleaseDate();
        this.priceOriginal = shoes.getPriceOriginal();
    }


}


