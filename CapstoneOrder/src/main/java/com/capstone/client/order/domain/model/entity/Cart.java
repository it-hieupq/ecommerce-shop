package com.capstone.client.order.domain.model.entity;

import com.capstone.client.order.domain.model.dto.response.CartDTO;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@IdClass(CartIdClass.class)
public class Cart {
    @Id
    @GeneratedValue
    private Integer cartId;
    @Id
    private Integer userId;

    public CartDTO toDTO() {
        return CartDTO.builder().cartId(this.cartId).build();
    }
}
