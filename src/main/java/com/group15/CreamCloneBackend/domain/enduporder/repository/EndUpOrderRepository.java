package com.group15.CreamCloneBackend.domain.enduporder.repository;

import com.group15.CreamCloneBackend.domain.enduporder.EndUpOrder;
import com.group15.CreamCloneBackend.domain.product.Shoes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EndUpOrderRepository extends JpaRepository<EndUpOrder, Long> {

    List<EndUpOrder> findAllByShoesOrderByMatchingPriceAsc(Shoes shoes);
}
