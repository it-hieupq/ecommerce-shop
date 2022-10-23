package com.capstone.client.order.domain.model.entity;

import com.capstone.client.order.domain.model.dto.response.OrderDTO;
import com.capstone.client.order.domain.model.dto.response.OrderItemDTO;
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
@IdClass(OrderItemIdClass.class)
public class OrderItem {
    @Id
    private Integer orderId;
    @Id
    private Integer productId;
    private Integer quantity;
    private Float itemPrice;
    private String itemName;

    public OrderItemDTO toDTO(){
        return OrderItemDTO.builder()
                .itemName(this.itemName)
                .itemPrice(this.itemPrice)
                .orderId(this.orderId)
                .productId(this.productId)
                .quantity(this.quantity)
                .build();
    }
}
