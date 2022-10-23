package com.capstone.client.order.domain.repository;

import com.capstone.client.order.domain.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepo extends JpaRepository<Cart, Integer> {
    Cart findByUserId(Integer userId);
}
