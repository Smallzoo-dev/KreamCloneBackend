package com.group15.CreamCloneBackend.domain.user.repository;

import com.group15.CreamCloneBackend.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
}
