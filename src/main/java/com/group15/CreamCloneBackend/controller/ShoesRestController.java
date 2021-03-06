package com.group15.CreamCloneBackend.controller;

import com.group15.CreamCloneBackend.domain.product.dto.MainReponseDto;
import com.group15.CreamCloneBackend.domain.product.dto.ShoesDto;
import com.group15.CreamCloneBackend.domain.product.dto.ShoesReponseDto;
import com.group15.CreamCloneBackend.domain.product.service.ShoesService;
import com.group15.CreamCloneBackend.security.UserDetailsImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ShoesRestController {

    private final ShoesService shoesService;


    @ApiOperation(value = "메인페이지",notes = "메인페이지 성공 , 실패 여부 반환")
    @GetMapping("/")
    public MainReponseDto getList(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        try {
            if (userDetails == null) {
                return new MainReponseDto(shoesService.getList(), 200L, "성공");
            } else {
                return new MainReponseDto(shoesService.getUserLoginedList(userDetails.getUser()), 200L, "성공");
            }
        } catch (Exception e){
            MainReponseDto mainReponseDto = new MainReponseDto();
            mainReponseDto.setStatusCode(500L);
            mainReponseDto.setMsg("상품목록을 불러오는데 실패하였습니다.");
            return mainReponseDto;
        }
    }

    @ApiOperation(value = "상세페이지",notes = "상세페이지 성공 , 실패 여부 반환")
    @GetMapping("/product/{productId}")
    public ShoesDto showShoes(@PathVariable Long productId){

        try {
            ShoesDto response = shoesService.showDto(productId);
            return response;
        } catch (Exception e) {
            ShoesDto shoesDto = new ShoesDto();
            shoesDto.setStatusCode(500L);
            shoesDto.setMsg(e.getMessage());
            return shoesDto;
        }

    }

}
