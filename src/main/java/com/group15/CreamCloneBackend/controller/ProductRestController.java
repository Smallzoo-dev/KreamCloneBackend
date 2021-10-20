package com.group15.CreamCloneBackend.controller;

import com.group15.CreamCloneBackend.domain.order.dto.SingleSizeResponseDto;
import com.group15.CreamCloneBackend.domain.order.dto.SizePriceResponseDto;
import com.group15.CreamCloneBackend.domain.order.service.OrderService;
import com.group15.CreamCloneBackend.security.UserDetailsImpl;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ProductRestController {
    private final OrderService orderService;

    @ApiOperation(value = "단일 사이즈 가격 조회.", notes = "최근 거래 가격, 구매 가격, 판매 가격")
    @GetMapping("/product/size/{productId}")
    public SingleSizeResponseDto size(@PathVariable Long productId,
                                      @RequestParam String size) {
        SingleSizeResponseDto singlePrice = orderService.getSinglePrice(productId, size);
        return singlePrice;
    }


    @ApiOperation(value = "모든 사이즈 즉시 구매가 조회.", notes = "사이즈별 가격 조회")
    @GetMapping("/product/sizeall/{productId}")
    public SizePriceResponseDto sizeAll(@PathVariable Long productId) {
        SizePriceResponseDto sizePrice = orderService.getSizePrice(productId);
        return sizePrice;
    }

}
