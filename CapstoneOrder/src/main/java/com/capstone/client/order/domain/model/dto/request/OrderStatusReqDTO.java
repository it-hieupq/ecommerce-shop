package com.capstone.client.order.domain.model.dto.request;

import com.capstone.client.order.domain.common.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusReqDTO {
    private OrderStatus orderStatus;
}
