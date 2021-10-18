package com.group15.CreamCloneBackend.domain.order.service;

import com.group15.CreamCloneBackend.domain.order.TradeType;
import com.group15.CreamCloneBackend.domain.order.TradingRole;
import com.group15.CreamCloneBackend.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

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
    public Long order(Long memberId, Long shoesId, TradingRole tradingRole, TradeType tradeType, String shoeSize, Long price) {
        return null;
    }

    @Override
    public void buyOrdercreate() {

    }

    @Override
    public void buyOrdermatch() {

    }

    @Override
    public void sellOrdercreate() {

    }

    @Override
    public void sellOrdermatch() {

    }


}
