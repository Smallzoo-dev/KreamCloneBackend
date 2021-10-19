package com.group15.CreamCloneBackend.domain.product.repository;

import com.group15.CreamCloneBackend.domain.product.Shoes;
import org.springframework.data.jpa.repository.JpaRepository;
<<<<<<< HEAD

public interface ShoesRepository extends JpaRepository<Shoes,Long> {
=======
import org.springframework.stereotype.Repository;

@Repository
public interface ShoesRepository extends JpaRepository<Shoes, Long> {

>>>>>>> origin/joonkyu
}
