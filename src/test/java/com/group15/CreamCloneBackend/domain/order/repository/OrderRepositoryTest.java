package com.group15.CreamCloneBackend.domain.order.repository;

import com.group15.CreamCloneBackend.domain.order.Order;
import com.group15.CreamCloneBackend.domain.order.TradingRole;
import com.group15.CreamCloneBackend.domain.product.Shoes;
import com.group15.CreamCloneBackend.domain.product.repository.ShoesRepository;
import com.group15.CreamCloneBackend.domain.user.User;
import com.group15.CreamCloneBackend.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles(value = "RepoTest")
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoesRepository shoesRepository;

    @Test
    public void orderTest() {
        User user = new User("jk", "12321");
        User user1 = new User("jk1", "12321");
        User user2 = new User("jk2", "12321");
        User user3 = new User("jk3", "12321");
        Shoes shoes = new Shoes();
        Shoes shoes1 = new Shoes();
        userRepository.save(user);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        shoesRepository.save(shoes);
        shoesRepository.save(shoes1);

        /**
         * given :
         * 등록 user는 상관 없음
         * shoes 종류 상관 있음
         * size 상관 있음
         * buy sell order 구분함
         */

        //다른 사이즈
        Order buy1 = Order.createOrder(user, shoes, TradingRole.fromString("buy"), "270", 200000L);
        Order buy2 = Order.createOrder(user1, shoes, TradingRole.fromString("buy"), "260", 200001L);
        // 다른 신발
        Order buy3 = Order.createOrder(user2, shoes1, TradingRole.fromString("buy"), "280", 200002L);
        Order buy4 = Order.createOrder(user3, shoes1, TradingRole.fromString("buy"), "280", 200003L);
        // 다른 order 종류
        Order buy5 = Order.createOrder(user1, shoes, TradingRole.fromString("sell"), "280", 200004L);
        Order buy6 = Order.createOrder(user2, shoes, TradingRole.fromString("sell"), "280", 200004L);
        // 기댓값
        Order buy7 = Order.createOrder(user, shoes, TradingRole.fromString("buy"), "280", 200004L);
        Order buy8 = Order.createOrder(user2, shoes, TradingRole.fromString("buy"), "280", 200005L);
        Order buy9 = Order.createOrder(user1, shoes, TradingRole.fromString("buy"), "280", 200006L);
        Order buy10 = Order.createOrder(user2, shoes, TradingRole.fromString("buy"), "280", 200007L);

        orderRepository.save(buy1);
        orderRepository.save(buy2);
        orderRepository.save(buy3);
        orderRepository.save(buy4);
        orderRepository.save(buy5);
        orderRepository.save(buy6);
        orderRepository.save(buy7);
        orderRepository.save(buy8);
        orderRepository.save(buy9);
        orderRepository.save(buy10);

        List<Order> wantToBuy = orderRepository.findAllByTradingRoleAndShoesAndShoesSizeOrderByPriceAsc(TradingRole.fromString("buy"), shoes, "280");

        //then
        Assertions.assertThat(wantToBuy.size()).isEqualTo(4);
        Assertions.assertThat(wantToBuy.get(3).getId()).isEqualTo(buy10.getId());
        Assertions.assertThat(wantToBuy.get(0).getId()).isEqualTo(buy7.getId());




    }

}