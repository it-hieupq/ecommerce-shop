package com.capstone.client.order.domain.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CartDTO {
    private Integer cartId;
    List<CartItemDTO> cartItemDTOList;
}