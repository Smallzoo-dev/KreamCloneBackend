package com.group15.CreamCloneBackend.domain.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MainReponseDto {
    private List<MainDto> productList;
    private Long statusCode;
    private String msg;
}
