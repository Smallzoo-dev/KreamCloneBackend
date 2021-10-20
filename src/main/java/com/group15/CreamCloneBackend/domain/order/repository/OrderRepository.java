package com.group15.CreamCloneBackend.domain.order.repository;

import com.group15.CreamCloneBackend.domain.order.Order;
import com.group15.CreamCloneBackend.domain.order.TradingRole;
import com.group15.CreamCloneBackend.domain.product.Shoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByTradingRoleAndShoesAndShoesSizeAndPriceOrderByCreatedAtDesc(TradingRole tradingRole, Shoes shoes,String shoesSize, Long price);

    List<Order> findAllByTradingRoleAndShoesAndShoesSizeOrderByPriceDesc(TradingRole tradingRole, Shoes shoes, String shoesSize);


    List<Order> findAllByShoesOrderByCreatedAtDesc(Shoes shoes);
}
