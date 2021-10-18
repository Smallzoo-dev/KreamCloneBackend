package com.group15.CreamCloneBackend.domain.user;

import com.group15.CreamCloneBackend.domain.order.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @OneToMany(mappedBy = "user", fetch = LAZY, cascade = CascadeType.ALL)
    private List<UserShoes> likedShoesList = new ArrayList<>();

    @OneToMany(mappedBy = "user",fetch = LAZY, cascade = CascadeType.ALL)
    private List<Order> joinedOrderList = new ArrayList<>();



}
