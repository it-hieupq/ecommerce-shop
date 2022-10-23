package com.capstone.client.order.domain.repository;

import com.capstone.client.order.domain.model.dto.response.CartItemDTO;
import com.capstone.client.order.domain.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCartId(Integer cartId);

    boolean existsByCartIdAndProductId(Integer cartId, Integer productId);

    CartItem findByCartIdAndProductId(Integer cartId, Integer productId);

    void deleteAllByCartId(Integer cartId);
}
