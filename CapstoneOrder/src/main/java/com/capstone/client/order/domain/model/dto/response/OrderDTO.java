package com.capstone.client.order.domain.model.dto.response;

import com.capstone.client.order.domain.common.OrderStatus;
import com.capstone.client.order.domain.common.PaymentMethod;
import com.capstone.client.order.domain.model.dto.request.OrderItemReqDTO;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@ToString
public class OrderDTO {
    private Integer orderId;
    private Integer userId;
    private OrderStatus orderStatus;
    private String orderDate;
    private String phoneNumber;
    private String address;
    private PaymentMethod paymentMethod;
    private String fullName;
    private Integer couponId;
    private Float total;
    List<OrderItemDTO> orderItem;
}
