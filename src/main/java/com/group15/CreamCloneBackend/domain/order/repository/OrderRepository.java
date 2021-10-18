package com.group15.CreamCloneBackend.domain.order.repository;

import com.group15.CreamCloneBackend.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * 판매 요청인 경우
     * db에서 유저가 전달한 판매 원하는 금액(즉시 판매 가
     */

    List<Order> findAllByPriceOrderByCreatedAtDesc(Long price);



    //구매인 경우
}
