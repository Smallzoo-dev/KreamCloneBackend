package com.group15.CreamCloneBackend.domain.order.service;

import com.group15.CreamCloneBackend.domain.enduporder.EndUpOrder;
import com.group15.CreamCloneBackend.domain.enduporder.repository.EndUpOrderRepository;
import com.group15.CreamCloneBackend.domain.order.Order;
import com.group15.CreamCloneBackend.domain.order.TradeType;
import com.group15.CreamCloneBackend.domain.order.TradingRole;
import com.group15.CreamCloneBackend.domain.order.dto.*;
import com.group15.CreamCloneBackend.domain.order.repository.OrderRepository;
import com.group15.CreamCloneBackend.domain.product.Shoes;
import com.group15.CreamCloneBackend.domain.product.repository.ShoesRepository;
import com.group15.CreamCloneBackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
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
                userRepository.findById(memberId).orElseThrow(()->new IllegalArgumentException("유저 정보가 존재하지 않습니다.")),
                shoesRepository.findById(shoesId).orElseThrow(()->new IllegalArgumentException("신발 정보가 존재하지 않습니다.")),
                tradingRole,
                shoesSize,
                price);

        orderRepository.save(order);
        return order.getId();
    }

    // 원하는 가격으로 구매 (구매 입찰 중 최저가 매물 order 삭제)
    @Override
    public Long buyOrdermatch(Long shoesId, Long userId, Long price, String size) {
        Shoes shoes = shoesRepository.findById(shoesId).orElseThrow(() -> new IllegalArgumentException("신발 정보가 존재하지 않습니다."));
        List<Order> matchList = orderRepository.findAllByTradingRoleAndShoesAndShoesSizeAndPriceOrderByCreatedAtDesc(SELLER, shoes, size, price);
        if (matchList.isEmpty()) {
            throw new IllegalArgumentException("거래중인 매물이 다른 유저에 의해 판매되었습니다.");
        }
        EndUpOrder endUpOrder = EndUpOrder.createEndUpOrder(
                shoes,
                price,
                userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저 정보가 존재하지 않습니다.")).getUsername(),
                matchList.get(0).getUser().getUsername());

        orderRepository.deleteById(matchList.get(0).getId());
        endUpOrderRepository.save(endUpOrder);
        return endUpOrder.getId();

    }

    // 원하는 가격으로 판매 매물 등록
    @Override
    public Long sellOrdercreate(Long memberId, Long shoesId, TradingRole tradingRole, String shoesSize, Long price) {
        Order order = Order.createOrder(
                userRepository.findById(memberId).orElseThrow(()->new IllegalArgumentException("유저 정보가 존재하지 않습니다.")),
                shoesRepository.findById(shoesId).orElseThrow(()->new IllegalArgumentException("신발 정보가 존재하지 않습니다.")),
                tradingRole,
                shoesSize,
                price);

        orderRepository.save(order);
        return order.getId();

    }

    // 구매 입찰중 가격 가장 높은 거래에 즉시 판매
    @Override
    public Long sellOrdermatch(Long shoesId, Long userId, String size , Long price) {
        Shoes shoes = shoesRepository.findById(shoesId).orElseThrow(() -> new IllegalArgumentException("신발 정보가 존재하지 않습니다."));
        List<Order> matchList = orderRepository.findAllByTradingRoleAndShoesAndShoesSizeAndPriceOrderByCreatedAtDesc(BUYER, shoes, size, price);
        if (matchList.isEmpty()) {
            throw new IllegalArgumentException("거래중인 매물이 다른 유저에 의해 판매되었습니다.");
        }

        EndUpOrder endUpOrder = EndUpOrder.createEndUpOrder(
                shoesRepository.findById(shoesId).orElseThrow(() -> new IllegalArgumentException("신발 정보가 존재하지 않습니다.")),
                price,
                matchList.get(0).getUser().getUsername(),
                userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("유저 정보가 존재하지 않습니다.")).getUsername());

        endUpOrderRepository.save(endUpOrder);
        orderRepository.deleteById(matchList.get(0).getId());
        return endUpOrder.getId();

    }



    // 사이즈별 매물 가격 조회
    public SizePriceResponseDto getSizePrice(Long shoesId) {
        List<SizeEachPrice> priceFromDB = findPriceFromDB(shoesId);
        return new SizePriceResponseDto(priceFromDB, 200L, "모든 사이즈 즉시 구매가 조회 성공");
    }

    // 사이즈별 매물 가격 조회 편의 메서드
    private List<SizeEachPrice> findPriceFromDB(Long shoesFound) {
        List<SizeEachPrice> sizeEachPriceList = new ArrayList<>();
        sizeEachPriceList.add(new SizeEachPrice("230", priceCheck("230", shoesFound)));
        sizeEachPriceList.add(new SizeEachPrice("240", priceCheck("240", shoesFound)));
        sizeEachPriceList.add(new SizeEachPrice("250", priceCheck("250", shoesFound)));
        sizeEachPriceList.add(new SizeEachPrice("260", priceCheck("260", shoesFound)));
        sizeEachPriceList.add(new SizeEachPrice("270", priceCheck("270", shoesFound)));
        sizeEachPriceList.add(new SizeEachPrice("280", priceCheck("280", shoesFound)));
        return sizeEachPriceList;
    }

    // 사이즈별 매물 가격 조회 편의 메서드
    private String priceCheck(String size, Long shoesId) {
        Shoes shoes = shoesRepository.findById(shoesId).orElseThrow(() -> new IllegalArgumentException("신발 정보가 존재하지 않습니다."));
        DecimalFormat decFormat = new DecimalFormat("###,###");
        List<Order> searchedOrder = orderRepository.findAllByTradingRoleAndShoesAndShoesSizeOrderByPriceDesc(SELLER, shoes, size);
        if (searchedOrder.size() == 0) {
            return "구매 입찰";
        }
        else
            return decFormat.format(searchedOrder.get(0).getPrice());
    }

    public SingleSizeResponseDto getSinglePrice(Long shoesId, String shoesSize) {
        DecimalFormat decFormat = new DecimalFormat("###,###");
        SingleSizeResponseDto singleSizeResponseDto = new SingleSizeResponseDto();
        Shoes shoes = shoesRepository.findById(shoesId).orElseThrow(() -> new IllegalArgumentException("신발 정보가 존재하지 않습니다."));


        //최근 거래 가격 세팅
        List<EndUpOrder> endUpOrderByShoes = endUpOrderRepository.findAllByShoesOrderByMatchingPriceDesc(shoes);
        if (endUpOrderByShoes.size() == 0) {
            singleSizeResponseDto.setPriceRecent("null");
            singleSizeResponseDto.setMsg("최근 거래내역 없음");
        }
        else {
            singleSizeResponseDto.setPriceRecent(decFormat.format(endUpOrderByShoes.get(0).getMatchingPrice()));
        }

        // 판매 가격 세팅
        List<Order> buyList = orderRepository.findAllByTradingRoleAndShoesAndShoesSizeOrderByPriceDesc(BUYER, shoes, shoesSize);
        if (buyList.size() == 0) {
            singleSizeResponseDto.setPriceSell("판매 입찰");
        } else {
            singleSizeResponseDto.setPriceSell(decFormat.format(buyList.get(0).getPrice()));
        }

        // 구매 가격 세팅
        List<Order> sellList = orderRepository.findAllByTradingRoleAndShoesAndShoesSizeOrderByPriceDesc(SELLER, shoes, shoesSize);
        if (sellList.size() == 0) {
            singleSizeResponseDto.setPriceBuy("구매 입찰");
        } else {
            singleSizeResponseDto.setPriceBuy(decFormat.format((sellList.get(0).getPrice())));
        }

        singleSizeResponseDto.setStatusCode(200L);

        if (singleSizeResponseDto.getMsg().equals("최근 거래내역 없음")) {
            return singleSizeResponseDto;
        } else {
            singleSizeResponseDto.setMsg("단일 사이즈 가격 조회 성공");
            return singleSizeResponseDto;
        }

    }



}
