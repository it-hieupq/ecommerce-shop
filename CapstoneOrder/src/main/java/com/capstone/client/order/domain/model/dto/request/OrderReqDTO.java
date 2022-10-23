package com.capstone.client.order.domain.model.dto.request;

import com.capstone.client.order.domain.common.OrderStatus;
import com.capstone.client.order.domain.common.PaymentMethod;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class OrderReqDTO {
    private Integer orderId;
    private Integer userId;
    private OrderStatus orderStatus;
    private String orderDate;
    private String phoneNumber;
    private String address;
    private PaymentMethod paymentMethod;
    private String fullName;
    private List<OrderItemReqDTO> orderItem;
    private Integer couponId;
    private Float total;
}