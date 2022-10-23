package com.capstone.client.order.domain.model.entity;

import com.capstone.client.order.domain.model.dto.response.CartItemDTO;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@IdClass(CartItemIdClass.class)
public class CartItem {
    @Id
    private Integer cartId;
    @Id
    private Integer productId;
    private Integer quantity;

    public CartItemDTO toDTO(){
        return CartItemDTO.builder().quantity(this.quantity).productId(this.productId).build();
    }
}
