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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderRestController {
    private final OrderService orderService;

    @ApiOperation(value = "거래 페이지", notes = "거래 성공, 실패여부 반환")
    @GetMapping("/order/transaction/{productid}")
    public OrderResponseDto transaction(@PathVariable Long productid,
                                        @RequestBody OrderRequestDto orderRequestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        OrderResponseDto response = orderService.order(
                userDetails.getUser().getId(),
                productid,
                TradingRole.fromString(orderRequestDto.getRequestType()),
                TradeType.fromString(orderRequestDto.getPurchaseType()),
                orderRequestDto.getSize(),
                orderRequestDto.getPriceExpected()
        );
        return response;
    }
}