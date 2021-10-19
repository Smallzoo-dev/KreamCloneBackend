package com.group15.CreamCloneBackend.domain.order.service;

import com.group15.CreamCloneBackend.domain.enduporder.EndUpOrder;
import com.group15.CreamCloneBackend.domain.enduporder.repository.EndUpOrderRepository;
import com.group15.CreamCloneBackend.domain.order.Order;
import com.group15.CreamCloneBackend.domain.order.TradeType;
import com.group15.CreamCloneBackend.domain.order.TradingRole;
import com.group15.CreamCloneBackend.domain.order.dto.OrderResponseDto;
import com.group15.CreamCloneBackend.domain.order.repository.OrderRepository;
import com.group15.CreamCloneBackend.domain.product.repository.ShoesRepository;
import com.group15.CreamCloneBackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.group15.CreamCloneBackend.domain.order.TradeType.*;
import static com.group15.CreamCloneBackend.domain.order.TradingRole.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ShoesRepository shoesRepository;
    private final UserRepository userRepository;
    private final EndUpOrderRepository endUpOrderRepository;


    /**
     * 판매 요청인 경우
     * 1. 유저가 입력한 판매 희망 가격이 즉시 판매 가능 가격보다 큰 가격인 경우
     *      -> db 조회 필요 없이 판매 order 생성함
     * 2. 유저가 입력한 판매 희망 가격이 즉시 판매 가능 금액인경우
     *      a) order table에서 최고 가격으로 조회 후 거래체결 msg response, 거래 완료 db에 쌓기
     *      b) 최고 가격의 구매입찰이 여러개인 경우 등록순서로 판매 완료
     *      c) 거래 도중 기존 최고가로 다른 사람통해 거래 채결되어 거래 할 수 없는경우 -> 예외처리
     * 3. 유저가 입력한 판매 희망 가격이 즉시 판매 가능 금액보다 낮은 경우
     *      -> 예외처리
     */

    /**
     * 구매 요청인 경우
     * 1. 유저가 입력한 구매 희망 가격이 즉시 구매 가능 가격보다 큰 가격인 경우
     *      -> 예외처리
     * 2. 유저가 입력한 구매 희망 가격이 즉시 구매 가능 금액인경우
     *      a) order table에서 tradingRole 판매 중인 최저 가격 검색 후 거래채결 msg response 거래 완료 db에 쌓기
     *      b) 최저 가격의 판매입찰이 여러개인 경우 등록 순서로 구매 완료
     *      c) 거래 도중 기존 최고가로 다른 사람통해 거래 채결되어 거래 할 수 없는경우 -> 예외처리
     * 3. 유저가 입력한 구매 희망 가격이 즉시 구매 가능 가격보다 저렴한 가격인 경우
     *      -> db 조회 필요 없이 order 생성함
     *
     */

    //분기용 메서드
    @Override
    public OrderResponseDto order(Long userId, Long shoesId, TradingRole tradingRole, TradeType tradeType, String shoeSize, Long price) {
        if (tradeType.equals(Bidding)) {
            if (tradingRole.equals(BUYER)) {
                Long orderId = buyOrdercreate(userId, shoesId, tradingRole, shoeSize, price);
                return new OrderResponseDto(200L, "희망 구매 가격으로 거래 입찰이 생성되었습니다.");
            } else {
                Long endUpOrderId = buyOrdermatch(shoesId, userId, price);
                return new OrderResponseDto(200L, "즉시 구매가로 거래가 완료되었습니다.");
            }
        } else if (tradeType.equals(Match)){
            if (tradingRole.equals(BUYER)) {
                Long orderId = sellOrdercreate(userId, shoesId, tradingRole, shoeSize, price);
                return new OrderResponseDto(200L, "희망 판매 가격으로 거래 입찰이 생성되었습니다.");
            } else {
                Long endUpOrderId = sellOrdermatch(shoesId, userId, price);
                return new OrderResponseDto(200L, "즉시 구매가로 거래가 완료되었습니다.");;
            }

        }

    }

    @Override
    public Long buyOrdercreate(Long memberId, Long shoesId, TradingRole tradingRole, String shoesSize, Long price) {
        Order order = Order.createOrder(
                //예외처리
                userRepository.findById(memberId).orElseThrow(()->new IllegalArgumentException("id not found")),
                shoesRepository.findById(shoesId).orElseThrow(()->new IllegalArgumentException("id not found")),
                tradingRole,
                shoesSize,
                price);

        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public Long buyOrdermatch(Long shoesId, Long userId, Long price) {
        List<Order> matchList = orderRepository.findAllByPriceOrderByCreatedAtDesc(price);

        EndUpOrder endUpOrder = EndUpOrder.createEndUpOrder(
                shoesRepository.findById(shoesId).orElseThrow(() -> new IllegalArgumentException("id not found")),
                price,
                userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("not found")).getUsername(),
                matchList.get(0).getUser().getUsername());

        endUpOrderRepository.save(endUpOrder);
        return endUpOrder.getId();

    }

    @Override
    public Long sellOrdercreate(Long memberId, Long shoesId, TradingRole tradingRole, String shoesSize, Long price) {
        Order order = Order.createOrder(
                userRepository.findById(memberId).orElseThrow(()->new IllegalArgumentException("id not found")),
                shoesRepository.findById(shoesId).orElseThrow(()->new IllegalArgumentException("id not found")),
                tradingRole,
                shoesSize,
                price);

        orderRepository.save(order);
        return order.getId();

    }

    @Override
    public Long sellOrdermatch(Long shoesId, Long userId, Long price) {
        List<Order> matchList = orderRepository.findAllByPriceOrderByCreatedAtDesc(price);

        EndUpOrder endUpOrder = EndUpOrder.createEndUpOrder(
                shoesRepository.findById(shoesId).orElseThrow(() -> new IllegalArgumentException("id not found")),
                price,
                matchList.get(0).getUser().getUsername(),
                userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("not found")).getUsername());

        endUpOrderRepository.save(endUpOrder);
        return endUpOrder.getId();

    }


}
