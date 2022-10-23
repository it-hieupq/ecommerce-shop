package com.capstone.client.order.domain.controller.user;

import com.capstone.client.order.domain.common.ResponseDTO;
import com.capstone.client.order.domain.common.Stringify;
import com.capstone.client.order.domain.model.dto.response.CouponDTO;
import com.capstone.client.order.domain.service.CouponService;
import com.capstone.client.order.domain.service.WebClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("UserCouponController")
@CrossOrigin(origins = Stringify.ORIGIN_FRONTEND_URL)
@RequestMapping("/coupons")
public class CouponController {

    @Autowired
    private CouponService couponService;

    @Autowired
    private WebClientService webClientService;

    @GetMapping("/users/{userId}")
    public ResponseEntity<Object> getAll(@PathVariable( name = "userId") Integer userId){

        Map<String, Object> map = new HashMap<>();
        HttpStatus status = HttpStatus.OK;
        String message = "Get all coupon by user successfully";
        List<CouponDTO> couponDTOList = null;

        try {
            if(!webClientService.userExist(userId)){
                message = "User not found";
                status = HttpStatus.NOT_FOUND;
            } else {
                couponDTOList = couponService.getAllByUserId(userId);
            }

        } catch (Exception e){
            message = e.getMessage();
            status = HttpStatus.EXPECTATION_FAILED;
            e.printStackTrace();
        }

        map.put("coupons", couponDTOList);

        return ResponseDTO.getResponse(message, status, map);
    }

}
