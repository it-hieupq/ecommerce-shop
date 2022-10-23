package com.capstone.client.order.domain.controller.user;

import com.capstone.client.order.domain.common.ResponseDTO;
import com.capstone.client.order.domain.common.Stringify;
import com.capstone.client.order.domain.model.dto.request.OrderReqDTO;
import com.capstone.client.order.domain.model.dto.response.OrderDTO;
import com.capstone.client.order.domain.model.entity.CouponUser;
import com.capstone.client.order.domain.service.CouponService;
import com.capstone.client.order.domain.service.CouponUserService;
import com.capstone.client.order.domain.service.OrderService;
import com.capstone.client.order.domain.service.WebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("UserOrderController")
@CrossOrigin(origins = Stringify.ORIGIN_FRONTEND_URL)
@RequestMapping("/users/{userId}/orders")
public class OrderController {

    @Autowired
    private WebClientService webClientService;
    @Autowired
    private OrderService orderService;

    @Autowired
    private CouponService couponService;

    @Autowired
    private CouponUserService couponUserService;

    @PostMapping("")
    public ResponseEntity<Object> placeAnOrder(@PathVariable(name = "userId") Integer userId, @RequestBody OrderReqDTO orderReqDTO){

        String message = "Your order is process in progress";
        HttpStatus status = HttpStatus.CREATED;
        Map<String, Object> map = new HashMap<>();
        OrderDTO orderDTO = null;
        try {
            if(!webClientService.userExist(userId)){
                message = "User not found";
                status = HttpStatus.NOT_FOUND;
            } else if( orderReqDTO.getCouponId() != null && (!couponService.isExisted(orderReqDTO.getCouponId()) ||
                    !couponUserService.isExisted(CouponUser.builder().couponId(orderReqDTO.getCouponId()).userId(orderReqDTO.getUserId()).build()))) {
                message = "Coupon not found";
                status = HttpStatus.NOT_FOUND;
            } else {
                orderDTO = orderService.placeAnOrder(userId, orderReqDTO);
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("order", orderDTO);
        return ResponseDTO.getResponse(message, status, map);
    }

    @GetMapping("")
    public ResponseEntity<Object> getAllByUser(@PathVariable(name = "userId") Integer userId) {

        String message = "Get orders by user";
        HttpStatus status = HttpStatus.OK;
        Map<String, Object> map = new HashMap<>();
        List<OrderDTO> orderDTOList = null;
        try {
            if(!webClientService.userExist(userId)){
                message = "User not found";
                status = HttpStatus.NOT_FOUND;
            } else {
                orderDTOList = orderService.getOrdersByUser(userId);
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("orders", orderDTOList);
        return ResponseDTO.getResponse(message, status, map);
    }
}
