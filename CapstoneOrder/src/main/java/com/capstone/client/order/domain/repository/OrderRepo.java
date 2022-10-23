package com.capstone.client.order.domain.repository;

import com.capstone.client.order.domain.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepo extends JpaRepository<OrderDetail, Integer>, JpaSpecificationExecutor<OrderDetail> {
    OrderDetail findByOrderId(Integer orderId);
}
