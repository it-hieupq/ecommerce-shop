package com.capstone.client.order.domain.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CartItemDTO {
    private Integer productId;
    private Integer quantity;
}