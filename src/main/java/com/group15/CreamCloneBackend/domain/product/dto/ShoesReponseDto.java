package com.group15.CreamCloneBackend.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoesReponseDto {
    private ShoesDto shoesDto;

    private Long statusCode;
    private String msg;
}
