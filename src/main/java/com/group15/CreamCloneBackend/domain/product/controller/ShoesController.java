package com.group15.CreamCloneBackend.domain.product.controller;

import com.group15.CreamCloneBackend.domain.product.Shoes;
import com.group15.CreamCloneBackend.domain.product.dto.MainDto;
import com.group15.CreamCloneBackend.domain.product.dto.ShoesDto;
import com.group15.CreamCloneBackend.domain.product.service.ShoesService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ShoesController {

    private final ShoesService shoesService;

//    @GetMapping("/")
//    public MainDto getList(){
//
//        return shoesService.getList();
//    }

    @ApiOperation(value = "상세페이지",notes = "상태 코드값, 메시지")
    @GetMapping("/product/{productId}")
    public ShoesDto showShoes(@PathVariable Long productId){

        return shoesService.showDto(productId);
    }
    @PostMapping("/test")
    public Shoes createTest(){

        return shoesService.createTest();
    }
}
