package com.group15.CreamCloneBackend.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ShoesDto {


    private String brand;
    private String modelName;
    private String image;
    private Long bookMarkCnt;
    private String modelNumber;
    private String releaseDate;
    private String priceOriginal;



}
