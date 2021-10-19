package com.group15.CreamCloneBackend.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserShoesRepository extends JpaRepository<UserShoes,Long> {
    UserShoes findByUser(User user);
}
