package com.group15.CreamCloneBackend.domain.order.repository;

import com.group15.CreamCloneBackend.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByPriceOrderByCreatedAtDesc(Long price);

}
