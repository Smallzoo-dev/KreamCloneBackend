package com.group15.CreamCloneBackend.domain.product.controller;

import com.group15.CreamCloneBackend.domain.product.Shoes;
import com.group15.CreamCloneBackend.domain.product.dto.MainDto;
import com.group15.CreamCloneBackend.domain.product.dto.MainReponseDto;
import com.group15.CreamCloneBackend.domain.product.dto.ShoesDto;
import com.group15.CreamCloneBackend.domain.product.service.ShoesService;
import com.group15.CreamCloneBackend.security.UserDetailsImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ShoesController {

    private final ShoesService shoesService;

    /**
     * 로그인 안 한 경우 : 거래가 = 발매가
     * 로그인 한 경우 : 거래가 = 발매가, (만약 거래가 있으면) 즉시구매가
     * @param userDetails
     * @return
     */
    @GetMapping("/")
    public MainReponseDto getList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return new MainReponseDto(shoesService.getList(),200L, "성공");
        } else {
            return new MainReponseDto(shoesService.getUserLoginedList(userDetails.getUser()), 200L, "성공");
        }
    }

    @ApiOperation(value = "상세페이지",notes = "상태 코드값, 메시지")
    @GetMapping("/product/{productId}")
    public ShoesDto showShoes(@PathVariable Long productId){

        return shoesService.showDto(productId);
    }

}
