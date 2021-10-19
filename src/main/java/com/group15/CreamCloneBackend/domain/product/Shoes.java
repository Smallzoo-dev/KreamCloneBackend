package com.group15.CreamCloneBackend.domain.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group15.CreamCloneBackend.domain.enduporder.EndUpOrder;
import com.group15.CreamCloneBackend.domain.order.Order;
import com.group15.CreamCloneBackend.domain.user.UserShoes;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Shoes {
    @Id
    @GeneratedValue
    private Long id;

    private String brandName;

    private String ImgUrl;

    private Long officialPrice;

    private String modelNum;


    @JsonIgnore
    @OneToMany(mappedBy = "shoes")
    private List<Order> inTradeList;

    @OneToMany(mappedBy = "shoes", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EndUpOrder> endUpTradeList;

    @OneToMany(mappedBy = "shoes", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<UserShoes> likedUserList;


}
