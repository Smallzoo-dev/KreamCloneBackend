package com.group15.CreamCloneBackend.controller;

import com.group15.CreamCloneBackend.domain.order.TradeType;
import com.group15.CreamCloneBackend.domain.order.TradingRole;
import com.group15.CreamCloneBackend.domain.order.dto.OrderRequestDto;
import com.group15.CreamCloneBackend.domain.order.dto.OrderResponseDto;
import com.group15.CreamCloneBackend.domain.order.service.OrderService;
import com.group15.CreamCloneBackend.security.UserDetailsImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService orderService;

    @ApiOperation(value = "거래 페이지", notes = "거래 성공, 실패여부 반환")
    @PostMapping("/order/transaction/{productid}")
    public OrderResponseDto transaction(@PathVariable Long productid,
                                        @RequestBody OrderRequestDto orderRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {


        if (orderRequestDto.isNullRequest()) {
            return new OrderResponseDto(500L, "잘못된 요청 입니다.");
        }


        try {
            OrderResponseDto response = orderService.order(
                    userDetails.getUser().getId(),
                    productid,
                    TradingRole.fromString(orderRequestDto.getRequestType()),
                    TradeType.fromString(orderRequestDto.getPurchaseType()),
                    orderRequestDto.getSize(),
                    orderRequestDto.getPriceExpected()
            );
            return response;
        } catch (Exception e) {
            OrderResponseDto response = new OrderResponseDto(500L, e.getMessage());
            return response;
        }
    }
}