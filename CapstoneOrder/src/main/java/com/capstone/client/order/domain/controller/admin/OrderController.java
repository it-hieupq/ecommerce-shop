package com.capstone.client.order.domain.controller.admin;

import com.capstone.client.order.domain.common.ResponseDTO;
import com.capstone.client.order.domain.common.Stringify;
import com.capstone.client.order.domain.model.dto.request.OrderStatusReqDTO;
import com.capstone.client.order.domain.model.dto.response.OrderDTO;
import com.capstone.client.order.domain.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("AdminOrderController")
@CrossOrigin(origins = Stringify.ORIGIN_FRONTEND_URL)
@RequestMapping("/admin/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/report")
    public ResponseEntity<Object> report(){
        String message = "Find sale report";
        HttpStatus status = HttpStatus.OK;
        Map<String, Object> map = new HashMap<>();
        List<OrderDTO> orderDTOList = null;

        try {
            orderDTOList = orderService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("orders", orderDTOList);

        return ResponseDTO.getResponse(message, status, map);
    }

    @GetMapping("")
    public ResponseEntity<Object> findAll(){
        String message = "Get all order successfully";
        HttpStatus status = HttpStatus.OK;
        Map<String, Object> map = new HashMap<>();
        List<OrderDTO> orderDTOList = null;

        try {
            orderDTOList = orderService.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("orders", orderDTOList);

        return ResponseDTO.getResponse(message, status, map);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Object> findById(@PathVariable(name = "orderId") Integer orderId) {

        String message = "Get order successfully";

        HttpStatus status = HttpStatus.OK;
        Map<String, Object> map = new HashMap<>();
        OrderDTO orderDTO = null;

        try {
            orderDTO = orderService.findById(orderId);
        } catch (Exception e) {
            e.printStackTrace();
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("order", orderDTO);

        return ResponseDTO.getResponse(message, status, map);
    }

    @PutMapping(value = "/{orderId}")
    public ResponseEntity<Object> updateOrderStatus(@PathVariable(name = "orderId") Integer orderId, @RequestBody OrderStatusReqDTO orderStatus) {

        String message = "Update order status successfully";
        HttpStatus status = HttpStatus.OK;

        Map<String, Object> map = new HashMap<>();
        OrderDTO orderDTO = null;

        try {
            if(orderService.isExisted(orderId)){
               orderDTO = orderService.updateOrderStatus(orderId, orderStatus.getOrderStatus());
            } else {
                message = "Order not found";
                status = HttpStatus.NOT_FOUND;
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
        }

        map.put("order", orderDTO);

        return ResponseDTO.getResponse(message, status, map);
    }

}
