package com.group15.CreamCloneBackend.domain.enduporder;

import com.group15.CreamCloneBackend.domain.product.Shoes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class EndUpOrder {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shoes_id")
    private Shoes shoes;

    private Long matchingPrice;

    private String buyer;

    private String seller;
}
