package com.sr.mobile_backend.repository;

import com.sr.mobile_backend.entity.OrderedItemDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepo extends JpaRepository<OrderedItemDetails,Long> {
}
