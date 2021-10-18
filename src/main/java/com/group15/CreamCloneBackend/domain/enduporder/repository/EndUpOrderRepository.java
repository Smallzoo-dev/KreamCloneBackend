package com.group15.CreamCloneBackend.domain.enduporder.repository;

import com.group15.CreamCloneBackend.domain.enduporder.EndUpOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EndUpOrderRepository extends JpaRepository<EndUpOrder, Long> {
}
