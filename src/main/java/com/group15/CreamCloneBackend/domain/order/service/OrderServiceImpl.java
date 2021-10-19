package com.group15.CreamCloneBackend.domain.order.service;

import com.group15.CreamCloneBackend.domain.enduporder.EndUpOrder;
import com.group15.CreamCloneBackend.domain.enduporder.repository.EndUpOrderRepository;
import com.group15.CreamCloneBackend.domain.order.Order;
import com.group15.CreamCloneBackend.domain.order.TradeType;
import com.group15.CreamCloneBackend.domain.order.TradingRole;
import com.group15.CreamCloneBackend.domain.order.dto.OrderResponseDto;
import com.group15.CreamCloneBackend.domain.order.dto.SingleSizeResponseDto;
import com.group15.CreamCloneBackend.domain.order.dto.SizePrice;
import com.group15.CreamCloneBackend.domain.order.dto.SizePriceResponseDto;
import com.group15.CreamCloneBackend.domain.order.repository.OrderRepository;
import com.group15.CreamCloneBackend.domain.product.Shoes;
import com.group15.CreamCloneBackend.domain.product.repository.ShoesRepository;
import com.group15.CreamCloneBackend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;

import static com.group15.CreamCloneBackend.domain.order.TradeType.*;
import static com.group15.CreamCloneBackend.domain.order.TradingRole.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ShoesRepository shoesRepository;
    private final UserRepository userRepository;
    private final EndUpOrderRepository endUpOrderRepository;


    //분기용 메서드
    @Override
    public OrderResponseDto order(Long userId, Long shoesId, TradingRole tradingRole, TradeType tradeType, String shoeSize, Long price) {
        // case : 원하는 가격으로 입찰
        if (tradeType.equals(Bidding)) {
            // case : 원하는 가격으로 입찰, 구매 원하는 경우 구매 거래 생성
            if (tradingRole.equals(BUYER)) {
                Long orderId = buyOrdercreate(userId, shoesId, tradingRole, shoeSize, price);
                return new OrderResponseDto(200L, "희망 구매 가격으로 거래 입찰이 생성되었습니다.");
            }
            // case : 원하는 가격으로 입찰, 판매 원하는 경우 판매 입찰 생성
            else if(tradingRole.equals(SELLER)) {

                Long orderId = sellOrdercreate(userId, shoesId, tradingRole, shoeSize, price);
                return new OrderResponseDto(200L, "희망 판매 가격으로 거래 입찰이 생성되었습니다.");

            }
        }
        // case : 즉시 구매 가격으로 거래
        else if (tradeType.equals(Match)){
            // case : 즉시 구매 가격으로 구매
            if (tradingRole.equals(BUYER)) {
                Long endUpOrderId = buyOrdermatch(shoesId, userId, price, shoeSize);
                return new OrderResponseDto(200L, "즉시 구매가로 거래가 완료되었습니다.");
            }
            // case : 즉시 구매 가격으로 판매
            else if(tradingRole.equals(SELLER)) {
                Long endUpOrderId = sellOrdermatch(shoesId, userId, shoeSize,price);
                return new OrderResponseDto(200L, "즉시 구매가로 거래가 완료되었습니다.");
            }

        }

        return new OrderResponseDto(500L, "거래가 실패했습니다. 다시 시도해 주세요.");

    }

    // 원하는 가격으로 구매 생성 (구매 입찰 생성)
    @Override
    public Long buyOrdercreate(Long memberId, Long shoesId, TradingRole tradingRole, String shoesSize, Long price) {
        Order order = Order.createOrder(
                //예외처리 나중에 다시
                userRepository.findById(memberId).orElseThrow(()->new IllegalArgumentException("id not found")),
                shoesRepository.findById(shoesId).orElseThrow(()->new IllegalArgumentException("id not found")),
                tradingRole,
                shoesSize,
                price);

        orderRepository.save(order);
        return order.getId();
    }

    // 원하는 가격으로 구매 (구매 입찰 중 최저가 매물 order 삭제)
    @Override
    public Long buyOrdermatch(Long shoesId, Long userId, Long price, String size) {
        Shoes shoes = shoesRepository.findById(shoesId).orElseThrow(() -> new IllegalArgumentException("shoes not found"));
        List<Order> matchList = orderRepository.findAllByTradingRoleAndShoesAndShoesSizeAndPriceOrderByCreatedAtDesc(SELLER, shoes, size, price);

        EndUpOrder endUpOrder = EndUpOrder.createEndUpOrder(
                shoesRepository.findById(shoesId).orElseThrow(() -> new IllegalArgumentException("id not found")),
                price,
                userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("not found")).getUsername(),
                matchList.get(0).getUser().getUsername());

        orderRepository.deleteById(matchList.get(0).getId());
        endUpOrderRepository.save(endUpOrder);
        return endUpOrder.getId();

    }

    // 원하는 가격으로 판매 매물 등록
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

    // 구매 입찰중 가격 가장 높은 거래에 즉시 판매
    @Override
    public Long sellOrdermatch(Long shoesId, Long userId, String size , Long price) {
        Shoes shoes = shoesRepository.findById(shoesId).orElseThrow(() -> new IllegalArgumentException("shoes not found"));
        List<Order> matchList = orderRepository.findAllByTradingRoleAndShoesAndShoesSizeAndPriceOrderByCreatedAtDesc(BUYER, shoes, size, price);

        EndUpOrder endUpOrder = EndUpOrder.createEndUpOrder(
                shoesRepository.findById(shoesId).orElseThrow(() -> new IllegalArgumentException("id not found")),
                price,
                matchList.get(0).getUser().getUsername(),
                userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("not found")).getUsername());

        endUpOrderRepository.save(endUpOrder);
        orderRepository.deleteById(matchList.get(0).getId());
        return endUpOrder.getId();

    }



    // 사이즈별 매물 가격 조회
    public SizePriceResponseDto getSizePrice(Long shoesId) {
        Shoes shoesFound = shoesRepository.findById(shoesId).orElseThrow(() -> new IllegalArgumentException("shoes not found"));
        SizePrice sizePrice = findPriceFromDB(shoesFound);
        return new SizePriceResponseDto(sizePrice, 200L, "모든 사이즈 즉시 구매가 조회 성공");
    }

    // 사이즈별 매물 가격 조회 편의 메서드
    private SizePrice findPriceFromDB(Shoes shoesFound) {
        SizePrice sizePrice = new SizePrice(
                priceCheck("230", shoesFound),
                priceCheck("240", shoesFound),
                priceCheck("250", shoesFound),
                priceCheck("260", shoesFound),
                priceCheck("270", shoesFound),
                priceCheck("280", shoesFound)
        );
        return sizePrice;
    }

    // 사이즈별 매물 가격 조회 편의 메서드
    private String priceCheck(String size, Shoes shoesFound) {
        DecimalFormat decFormat = new DecimalFormat("###,###");
        List<Order> searchedOrder = orderRepository.findAllByTradingRoleAndShoesAndShoesSizeOrderByPriceDesc(SELLER, shoesFound, size);
        if (searchedOrder == null) {
            return "구매 입찰";
        }
        else
            return decFormat.format(searchedOrder.get(0).getPrice());
    }

    public SingleSizeResponseDto getSinglePrice(Long shoesId, String shoesSize) {
        DecimalFormat decFormat = new DecimalFormat("###,###");
        SingleSizeResponseDto singleSizeResponseDto = new SingleSizeResponseDto();
        Shoes shoes = shoesRepository.findById(shoesId).orElseThrow(() -> new IllegalArgumentException("shoes not found"));


        //최근 거래 가격 세팅
        List<EndUpOrder> endUpOrderByShoes = endUpOrderRepository.findAllByShoesAndOrderByMatchingPriceDesc(shoes);
        if (endUpOrderByShoes == null) {
            singleSizeResponseDto.setPriceRecent("null");
            singleSizeResponseDto.setMsg("최근 거래내역 없음");
        }
        else {
            singleSizeResponseDto.setPriceRecent(decFormat.format(endUpOrderByShoes.get(0).getMatchingPrice()));
        }

        // 판매 가격 세팅
        List<Order> buyList = orderRepository.findAllByTradingRoleAndShoesAndShoesSizeOrderByPriceDesc(BUYER, shoes, shoesSize);
        if (buyList == null) {
            singleSizeResponseDto.setPriceSell("판매 입찰");
        } else {
            singleSizeResponseDto.setPriceSell(decFormat.format(buyList.get(0).getPrice()));
        }

        // 구매 가격 세팅
        List<Order> sellList = orderRepository.findAllByTradingRoleAndShoesAndShoesSizeOrderByPriceDesc(SELLER, shoes, shoesSize);
        if (sellList == null) {
            singleSizeResponseDto.setPriceBuy("구매 입찰");
        } else {
            singleSizeResponseDto.setPriceBuy(decFormat.format((sellList.get(-1).getPrice())));
        }

        singleSizeResponseDto.setStatusCode(200L);

        if (singleSizeResponseDto.getMsg() == "최근 거래내역 없음") {
            return singleSizeResponseDto;
        } else {
            singleSizeResponseDto.setMsg("단일 사이즈 가격 조회 성공");
            return singleSizeResponseDto;
        }
        
    }



}
