package com.group15.CreamCloneBackend.controller;

import com.group15.CreamCloneBackend.domain.product.ProductDto;
import com.group15.CreamCloneBackend.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SetUpSheosRestController {

    private final ProductService productService;

    @PostMapping("/product/sheosDataSetup")
    public String setupSheos(@RequestBody ProductDto productDto){

        productService.setup(productDto);

        return "성공";

    }
}
