package com.capstone.client.order.domain.model.dto.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemReqDTO {
    private Integer orderId;
    private Integer productId;
    private Integer quantity;
    private Float itemPrice;
    private String itemName;
}
