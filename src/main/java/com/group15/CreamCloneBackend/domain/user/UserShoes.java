package com.group15.CreamCloneBackend.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.group15.CreamCloneBackend.domain.product.Shoes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserShoes {
    @Id
    @GeneratedValue
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    @JsonIgnore
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shoesId")
    @JsonIgnore
    Shoes shoes;
}