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


    MainDto(Shoes shoes){
        this.brand = shoes.getBrand();
        this.modelName = shoes.getModelName();
        this.image = shoes.getImage();
        this.bookMark = shoes.getBookmark();
        this.price = shoes.getPrice();
    }
}
//    public Shoes(ProductDto productDto){
//        this.bookmarkCnt = 0L;
//        this.image = productDto.getImage();
//        this.brand = productDto.getBrand();
//        this.modelNumber = productDto.getModelNumber();
//        this.modelName = productDto.getModelName();
//        this.priceOriginal = productDto.getPriceOriginal();
//        this.releaseDate = productDto.getReleaseDate();
//    }
