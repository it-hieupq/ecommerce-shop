package com.capstone.client.order.domain.service;

import com.capstone.client.order.domain.model.dto.request.OrderItemReqDTO;
import com.capstone.client.order.domain.model.dto.response.OrderItemDTO;
import com.capstone.client.order.domain.model.entity.OrderItem;
import com.capstone.client.order.domain.repository.OrderItemRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemService {

    @Autowired
    private WebClientService webClientService;

    @Autowired
    private OrderItemRepo orderItemRepo;

    public List<OrderItemDTO> saveAll(Integer orderId, List<OrderItemReqDTO> orderItemReqDTOList){
        List<OrderItem> orderItemList = new ArrayList<>();

        for(OrderItemReqDTO item: orderItemReqDTOList){
            if(webClientService.productExist(item.getProductId())){
                OrderItem tmp = OrderItem.builder()
                        .orderId(orderId)
                        .itemName(item.getItemName())
                        .itemPrice(item.getItemPrice())
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .build();
                orderItemList.add(tmp);
            }
        }

        if(!orderItemList.isEmpty()){
            return toDTOList( orderItemRepo.saveAll(orderItemList) );
        }
        return null;
    }

    public List<OrderItemDTO> toDTOList(List<OrderItem> list){
        return list.stream().map(OrderItem::toDTO).collect(Collectors.toList());
    }

    public List<OrderItemDTO> findByOrderId(Integer orderId) {
        return toDTOList(orderItemRepo.findByOrderId(orderId));
    }
}
