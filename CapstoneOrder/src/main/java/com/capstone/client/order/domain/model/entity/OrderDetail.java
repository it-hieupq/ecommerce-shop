package com.capstone.client.order.domain.model.entity;

import com.capstone.client.order.domain.common.OrderStatus;
import com.capstone.client.order.domain.common.PaymentMethod;
import com.capstone.client.order.domain.model.dto.response.OrderDTO;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue
    private Integer orderId;
    @Column(nullable = false)
    private Integer userId;
    @Column(nullable = false)
    private OrderStatus orderStatus;
    @Column(nullable = false)
    private String orderDate;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private PaymentMethod paymentMethod;
    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private Float total;

    private Integer couponId;

    public OrderDTO toDTO(){
        return OrderDTO.builder()
                .orderId(this.orderId)
                .userId(this.userId)
                .orderStatus(this.orderStatus)
                .phoneNumber(this.phoneNumber)
                .fullName(this.fullName)
                .orderDate(this.orderDate)
                .address(this.address)
                .paymentMethod(this.paymentMethod)
                .total(this.total)
                .build();
    }

}
