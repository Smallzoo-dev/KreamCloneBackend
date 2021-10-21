package com.group15.CreamCloneBackend.controller;

import com.group15.CreamCloneBackend.domain.product.dto.ProductDto;
import com.group15.CreamCloneBackend.domain.product.service.ProductService;
import com.group15.CreamCloneBackend.domain.product.repository.ShoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SetUpSheosRestController {

    private final ProductService productService;
    private final ShoesRepository shoesRepository;


    @PostMapping("/product/sheosDataSetup")
    public String setupSheos(@RequestBody ProductDto productDto){

        productService.setup(productDto);

        return "성공";

    }

    @GetMapping("/product/dropTable")
    public String deleteSheos(){

        shoesRepository.deleteAll();
        return "삭제됨 ";

    }
}
