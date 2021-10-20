package com.group15.CreamCloneBackend.domain.user.repository;

import com.group15.CreamCloneBackend.domain.product.Shoes;
import com.group15.CreamCloneBackend.domain.user.User;
import com.group15.CreamCloneBackend.domain.user.UserShoes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserShoesRepository extends JpaRepository<UserShoes,Long> {
    UserShoes findByUserAndShoes(User user, Shoes shoes);

}