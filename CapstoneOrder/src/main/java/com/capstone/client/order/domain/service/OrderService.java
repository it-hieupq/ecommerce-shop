package com.capstone.client.order.domain.service;

import com.capstone.client.order.domain.common.OrderStatus;
import com.capstone.client.order.domain.model.dto.request.OrderReqDTO;
import com.capstone.client.order.domain.model.dto.response.OrderDTO;
import com.capstone.client.order.domain.model.dto.response.OrderItemDTO;
import com.capstone.client.order.domain.model.entity.OrderDetail;
import com.capstone.client.order.domain.model.entity.OrderItem;
import com.capstone.client.order.domain.model.entity.filter.OrderFilter;
import com.capstone.client.order.domain.repository.OrderRepo;
import com.capstone.client.order.domain.repository.specification.OrderSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderItemService orderItemService;

    public boolean isExisted(Integer orderId){
        return orderRepo.existsById(orderId);
    }

    public OrderDTO placeAnOrder(Integer userId, OrderReqDTO orderReqDTO) {

        OrderDetail order = orderRepo.save(toOrder(orderReqDTO));

        if(order.getOrderId() != null){
            List<OrderItemDTO> orderItemDTOList = orderItemService.saveAll(order.getOrderId(), orderReqDTO.getOrderItem());
            cartService.clearCart(userId);
            return OrderDTO.builder()
                    .orderId(order.getOrderId())
                    .userId(order.getUserId())
                    .orderItem(orderItemDTOList)
                    .paymentMethod(order.getPaymentMethod())
                    .address(order.getAddress())
                    .orderDate(order.getOrderDate())
                    .fullName(order.getFullName())
                    .orderStatus(order.getOrderStatus())
                    .phoneNumber(order.getPhoneNumber())
                    .total(order.getTotal())
                    .couponId(order.getCouponId())
                    .build();
        }

        return null;
    }

    public OrderDetail toOrder(OrderReqDTO orderReqDTO) {

        return OrderDetail.builder()
                .orderId(orderReqDTO.getOrderId())
                .userId(orderReqDTO.getUserId())
                .orderStatus(orderReqDTO.getOrderStatus())
                .address(orderReqDTO.getAddress())
                .orderDate(orderReqDTO.getOrderDate())
                .fullName(orderReqDTO.getFullName())
                .paymentMethod(orderReqDTO.getPaymentMethod())
                .phoneNumber(orderReqDTO.getPhoneNumber())
                .couponId(orderReqDTO.getOrderId())
                .total(orderReqDTO.getTotal())
                .build();
    }

    public List<OrderDTO> findAll() {
        return toDTOList(orderRepo.findAll());
    }

    private List<OrderDTO> toDTOList(List<OrderDetail> list) {
        if(list == null || list.isEmpty()) return Collections.emptyList();
        return list.stream().map(OrderDetail::toDTO).collect(Collectors.toList());
    }

    public OrderDTO findById(Integer orderId) {
        OrderDTO order = orderRepo.findByOrderId(orderId).toDTO();
        List<OrderItemDTO> orderItemDTOList = orderItemService.findByOrderId(orderId);
        order.setOrderItem(orderItemDTOList);
        return order;
    }

    public OrderDTO updateOrderStatus(Integer orderId,OrderStatus orderStatus) {
        OrderDetail order =  orderRepo.findByOrderId(orderId);
        order.setOrderStatus(orderStatus);
        return orderRepo.save(order).toDTO();
    }

    public List<OrderDTO> getOrdersByUser(Integer userId) {
        OrderFilter filter = OrderFilter.builder().userIdList(Arrays.asList(userId)).build();
        return toDTOList(orderRepo.findAll(OrderSpecification.search(filter)));
    }
}
